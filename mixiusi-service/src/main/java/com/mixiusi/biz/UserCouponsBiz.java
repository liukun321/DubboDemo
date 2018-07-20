package com.mixiusi.biz;

import com.mixiusi.bean.Coupons;
import com.mixiusi.bean.UserCoupons;
import com.mixiusi.bean.WechatUser;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.bean.vo.WechatUserVo;
import com.mixiusi.repository.read.UserCouponsReadRepository;
import com.mixiusi.repository.read.WechatUserReadRepository;
import com.mixiusi.repository.write.UserCouponsRepository;
import com.mixiusi.repository.write.WechatUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
@Transactional
@Service
public class UserCouponsBiz {
	@Autowired
	private UserCouponsRepository userCouponsRepository;
	@Autowired
	private WechatUserRepository wechatUserRepository;
	@Autowired
	private UserCouponsReadRepository userCouponsReadRepository;
	@Autowired
	private WechatUserReadRepository wechatUserReadRepository;

	public List<Coupons> queryCoupons(String openId) {
		WechatUser user =  wechatUserReadRepository.findOne(openId);
		if(null != user){
			return userCouponsReadRepository.findByUid(user);
		}
		return null;
	}


	public UserCoupons queryCouponsById(Coupons coupons) {
		return userCouponsReadRepository.findByCid(coupons);
	}

	public Page<UserCoupons> findAllCoupons(WechatUserVo wvo) {
		Integer page = wvo.getPage();
		Integer size = wvo.getSize();
		Pageable pageable = null;
		if(StringUtils.isNull(wvo.getSort())) {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");
		}else {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, wvo.getSort());
		}
		return userCouponsReadRepository.findAll(new Specification<UserCoupons>() {
			@Override
			public Predicate toPredicate(Root<UserCoupons> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				
				if(!StringUtils.isNull(wvo.getOpenId())) {
					list.add(cb.equal(root.get("uid").as(WechatUser.class), wechatUserRepository.findOne(wvo.getOpenId())));
				}
			Predicate[] p = new Predicate[list.size()];  
            return cb.and(list.toArray(p)); 
			}
		}, pageable);
	}


	public Long findCouponsNumber(String openId) {
		return userCouponsReadRepository.count(new Specification<UserCoupons>() {
			@Override
			public Predicate toPredicate(Root<UserCoupons> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				
				if(!StringUtils.isNull(openId)) {
					list.add(cb.equal(root.get("uid").as(WechatUser.class), wechatUserRepository.findOne(openId)));
				}
			Predicate[] p = new Predicate[list.size()];  
            return cb.and(list.toArray(p)); 
			}
		});
	}

}
