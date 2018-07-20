package com.mixiusi.biz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mixiusi.repository.read.MobilePayReadRepository;
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

import com.mixiusi.bean.MobilePay;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.bean.vo.MobilePayDetailVo;
import com.mixiusi.bean.vo.MobilePayVo;
import com.mixiusi.repository.write.MobilePayRepository;

@Service
public class MobilePayBiz{
	private final Logger log = LoggerFactory.getLogger(MobilePayBiz.class);
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private MobilePayRepository mobilePayRepository;
	@Autowired
	private MobilePayReadRepository mobilePayReadRepository;

	public MobilePay queryMobilePay(String indentId) {
		return mobilePayReadRepository.findOne(indentId);
	}

	public void add(MobilePay pay) {
		mobilePayRepository.save(pay);
		
	}

	public Page<MobilePay> queryMobilePay(MobilePayVo mvo) {
		Integer page = mvo.getPage();
		Integer size = mvo.getSize();
		String sort = mvo.getSort();
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Pageable pageable = null;
		if(StringUtils.isNull(sort)) {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, "indentId");
		}else {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, sort);
		}
		return mobilePayReadRepository.findAll(new Specification<MobilePay>() {
			@Override
			public Predicate toPredicate(Root<MobilePay> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				
				if(!StringUtils.isNull(mvo.getIndentId())) {
					list.add(cb.equal(root.get("indentId").as(String.class), mvo.getIndentId()));
				}
				if(!StringUtils.isNull(mvo.getOpenId())) {
					list.add(cb.equal(root.get("openId").as(String.class), mvo.getOpenId()));
				}
//				if(!StringUtils.isNull(mvo.getMachineId())) {
//					list.add(cb.equal(root.get("machineId").as(String.class), mvo.getMachineId()));
//				}
//				if(!StringUtils.isNull(mvo.getOver())) {
//					list.add(cb.equal(root.get("over").as(Boolean.class), mvo.getOver()));
//				}
//				if(!StringUtils.isNull(mvo.getTotalCup())) {
//					list.add(cb.ge(root.get("totalCup").as(Integer.class), mvo.getTotalCup()));
//				}
				Date startTime = mvo.getStartTime();
				Date endTime = mvo.getEndTime();
				if(!StringUtils.isNull(startTime) || !StringUtils.isNull(endTime)) {
					 try {
						 if(StringUtils.isNull(startTime)) {
							 list.add(cb.lessThan(root.get("payTime").as(Date.class),
										sdfmat.parse(sdfmat.format(mvo.getEndTime()))));
						 }else if(StringUtils.isNull(endTime)) {
							 list.add(cb.greaterThan(root.get("payTime").as(Date.class), 
										sdfmat.parse(sdfmat.format(mvo.getStartTime()))));
						 }else {
							 list.add(cb.between(root.get("payTime").as(Date.class), 
									 sdfmat.parse(sdfmat.format(mvo.getStartTime())),
									 sdfmat.parse(sdfmat.format(mvo.getEndTime()))));
						 }
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				 }
				
			Predicate[] p = new Predicate[list.size()];  
            return cb.and(list.toArray(p)); 
			}
		}, pageable);
	}

	public Double querySumprice() {
		return mobilePayReadRepository.querySumPrice();
	}

	public MobilePay queryMobilePayById(String indentId) {
		return mobilePayReadRepository.findOne(indentId);
	}

	public Long querySumNumber(MobilePayVo mvo) {
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return mobilePayReadRepository.count(new Specification<MobilePay>() {
			@Override
			public Predicate toPredicate(Root<MobilePay> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				
				if(!StringUtils.isNull(mvo.getIndentId())) {
					list.add(cb.equal(root.get("indentId").as(String.class), mvo.getIndentId()));
				}
				if(!StringUtils.isNull(mvo.getOpenId())) {
					list.add(cb.equal(root.get("openId").as(String.class), mvo.getOpenId()));
				}
//				if(!StringUtils.isNull(mvo.getMachineId())) {
//					list.add(cb.equal(root.get("machineId").as(String.class), mvo.getMachineId()));
//				}
//				if(!StringUtils.isNull(mvo.getOver())) {
//					list.add(cb.equal(root.get("over").as(Boolean.class), mvo.getOver()));
//				}
//				if(!StringUtils.isNull(mvo.getTotalCup())) {
//					list.add(cb.ge(root.get("totalCup").as(Integer.class), mvo.getTotalCup()));
//				}
				Date startTime = mvo.getStartTime();
				Date endTime = mvo.getEndTime();
				if(!StringUtils.isNull(startTime) || !StringUtils.isNull(endTime)) {
					 try {
						 if(StringUtils.isNull(startTime)) {
							 list.add(cb.lessThan(root.get("payTime").as(Date.class),
										sdfmat.parse(sdfmat.format(mvo.getEndTime()))));
						 }else if(StringUtils.isNull(endTime)) {
							 list.add(cb.greaterThan(root.get("payTime").as(Date.class), 
										sdfmat.parse(sdfmat.format(mvo.getStartTime()))));
						 }else {
							 list.add(cb.between(root.get("payTime").as(Date.class), 
									 sdfmat.parse(sdfmat.format(mvo.getStartTime())),
									 sdfmat.parse(sdfmat.format(mvo.getEndTime()))));
						 }
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				 }
			Predicate[] p = new Predicate[list.size()];  
            return cb.and(list.toArray(p)); 
			}
		});
	}

	public void updateOrder(MobilePay mp) {
		mobilePayRepository.save(mp);
	}
	/**
	 * 批量删除微信端订单
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean removeMobilePay(List<String> indentIds) {
		for(String id : indentIds) {
			MobilePay coffee = mobilePayReadRepository.findOne(id);
			if(null != coffee) {
				entityManager.remove(coffee);
			}else {
				return false;
			}
		}
		entityManager.flush();
		entityManager.clear();
		return true;
	}

	public Page<MobilePay> queryMobilePayDetail(MobilePayDetailVo mpdvo) {
		Integer page = mpdvo.getPage();
		Integer size = mpdvo.getSize();
		String sort = mpdvo.getSort();
		Pageable pageable = null;
		if(StringUtils.isNull(sort)) {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, "indentId");
		}else {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, sort);
		}
		return mobilePayReadRepository.findAll(new Specification<MobilePay>() {
			@Override
			public Predicate toPredicate(Root<MobilePay> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				
				if(!StringUtils.isNull(mpdvo.getIndentId())) {
					list.add(cb.equal(root.get("indentId").as(String.class), mpdvo.getIndentId()));
				}
			Predicate[] p = new Predicate[list.size()];  
            return cb.and(list.toArray(p)); 
			}
		}, pageable);
	}

	public Long queryDetailSumNumber(MobilePayDetailVo mpdvo) {
		return mobilePayReadRepository.count(new Specification<MobilePay>() {
			@Override
			public Predicate toPredicate(Root<MobilePay> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				
				if(!StringUtils.isNull(mpdvo.getIndentId())) {
					list.add(cb.equal(root.get("indentId").as(String.class), mpdvo.getIndentId()));
				}
			Predicate[] p = new Predicate[list.size()];  
            return cb.and(list.toArray(p)); 
			}
		});
	}

	public Double queryUserSumprice(String openId) {
		return mobilePayReadRepository.queryUserSumPrice(openId);
	}

}
