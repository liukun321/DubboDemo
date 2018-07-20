package com.mixiusi.biz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mixiusi.repository.read.CouponsReadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mixiusi.bean.Coupons;
import com.mixiusi.bean.CouponsVo;
import com.mixiusi.bean.utils.CouponsCreateUtil;
import com.mixiusi.bean.utils.RandomStringGenerator;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.bean.vo.CouponVo;
import com.mixiusi.repository.write.CouponsRepository;
import com.mixiusi.service.CouponsServiceImpl;
@Transactional
@Service
public class CouponsBiz {
	private final Logger log = LoggerFactory.getLogger(CouponsServiceImpl.class);
	private Integer COUPONS_TYPE1 = 1;
	private Integer COUPONS_TYPE2 = 2;
	private Integer COUPONS_TYPE3 = 3;
	@Autowired
	private CouponsRepository couponsRepository;
	@Autowired
	private CouponsReadRepository couponsReadRepository;
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;

	public Coupons queryCouponsByCode(String couponCode) {
		Date date = new Date();
		Coupons coupons = couponsReadRepository.findByCouponCodeAndEndTimeAfter(couponCode, date);
		if(null == coupons)
			return null;
		boolean isuse = coupons.getIs_use();
		if(isuse) {
			return null;
		}
		return coupons;
	}

	public Boolean addCoupons(CouponsVo couponsVo) {
		Integer type = couponsVo.getType();
		Integer count = couponsVo.getCount();
		Date endTime = couponsVo.getEndTime();
		Integer money = couponsVo.getSumMoney();
		Map<String, Integer> coffeeIds = couponsVo.getCoffeeIds();
		Boolean flag = false;
		try {
			if (COUPONS_TYPE1 == type) {
				List<Double> values = couponsVo.getDiscounts();
				int size = values.size();
				for (int i = 0; i < count; i++) {
					String code = null;
					while (StringUtils.isNull(code)) {
						//如果拿到的优惠券的码是null则重复调用这个方法
						code = createCouponsCode();
					}
					String value = values.get(i % size).toString();
					Coupons coupons = new Coupons();
					coupons.setCouponCode(code);
					coupons.setIs_use(false);
					coupons.setEndTime(endTime);
					coupons.setType(type);
					coupons.setValue(value);
					entityManager.persist(coupons);
				}
			}
			if (COUPONS_TYPE2 == type) {
				List<Integer> values = CouponsCreateUtil.typeOfCoupons2(money * 100, count);
				for (int i = 0; i < count; i++) {
					String code = null;
					while (StringUtils.isNull(code)) {
						//如果拿到的优惠券的码是null则重复调用这个方法
						code = createCouponsCode();
					}
					Double value = Double.valueOf(values.get(i))/100;
					Coupons coupons = new Coupons();
					coupons.setCouponCode(code);
					coupons.setIs_use(false);
					coupons.setEndTime(endTime);
					coupons.setType(type);
					coupons.setValue(value.toString());
					entityManager.persist(coupons);
				}
			}
			if (COUPONS_TYPE3 == type) {
				for (Entry<String, Integer> coffeeId : coffeeIds.entrySet()) {
					for (int i = 0; i < coffeeId.getValue(); i++) {
						String code = null;
						while (StringUtils.isNull(code)) {
							//如果拿到的优惠券的码是null则重复调用这个方法
							code = createCouponsCode();
						}
						String value = coffeeId.getKey();
						Coupons coupons = new Coupons();
						coupons.setCouponCode(code);
						coupons.setIs_use(false);
						coupons.setEndTime(endTime);
						coupons.setType(type);
						coupons.setValue(value);
						entityManager.persist(coupons);
					}
				}
			}
			entityManager.flush();
			entityManager.clear();
			flag = true;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return flag;
	}

	public Coupons updateCoupons(Coupons coupons) {
		return couponsRepository.save(coupons);
	}

	
	private String createCouponsCode() {
		String code = RandomStringGenerator.getRandomInteger(6);
		//防止优惠券出现相同的值
		Coupons cp = this.queryCouponsByCode(code);
		if(null == cp) {
			return code;
		}
		return null;
	}
	
	
	public Page<Coupons> queryCoupons(CouponVo cvo) {
		Integer page = cvo.getPage();
		Integer size = cvo.getSize();
		String sort = cvo.getSort();
		Pageable pageable = null;
		if(StringUtils.isNull(sort)) {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");
		}else {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, sort);
		}
		return couponsReadRepository.findAll(new Specification<Coupons>() {
			@Override
			public Predicate toPredicate(Root<Coupons> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if(!StringUtils.isNull(cvo.getCouponCode())) {
					list.add(cb.equal(root.get("couponCode").as(String.class), cvo.getCouponCode()));
				}
				Predicate[] p = new Predicate[list.size()];  
	            return cb.and(list.toArray(p)); 
			}
		}, pageable);
	}

	public Long queryCouponsNumber(CouponVo cvo) {
		
		return couponsReadRepository.count(new Specification<Coupons>() {
			@Override
			public Predicate toPredicate(Root<Coupons> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if(!StringUtils.isNull(cvo.getCouponCode())) {
					list.add(cb.equal(root.get("couponCode").as(String.class), cvo.getCouponCode()));
				}
				Predicate[] p = new Predicate[list.size()];  
	            return cb.and(list.toArray(p)); 
			}
		});
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean removeCouponsByCode(List<String> couponsCodes) {
		for(String id : couponsCodes) {
			Coupons coupons = couponsReadRepository.findByCouponCode(id);
			if(null != coupons) {
				entityManager.remove(coupons);
			}else {
				return false;
			}
		}
		entityManager.flush();
		entityManager.clear();
		return true;
	}
}
