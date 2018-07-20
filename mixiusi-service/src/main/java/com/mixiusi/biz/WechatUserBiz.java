package com.mixiusi.biz;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mixiusi.repository.read.WechatUserReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mixiusi.bean.WechatUser;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.bean.vo.WechatUserVo;
import com.mixiusi.repository.write.WechatUserRepository;
@Transactional
@Service
public class WechatUserBiz {
	@Autowired
	private WechatUserRepository wechatUserRepository;
	@Autowired
	private WechatUserReadRepository wechatUserReadRepository;

	public Page<WechatUser> findAll(WechatUserVo uvo) {
		Integer page = uvo.getPage();
		Integer size = uvo.getSize();
		Pageable pageable = null;
		if(StringUtils.isNull(uvo.getSort())) {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, "openId");
		}else {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, uvo.getSort());
		}
		return wechatUserReadRepository.findAll(new Specification<WechatUser>() {
			@Override
			public Predicate toPredicate(Root<WechatUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				
				if(!StringUtils.isNull(uvo.getOpenId())) {
					list.add(cb.equal(root.get("openId").as(String.class), uvo.getOpenId()));
				}
			Predicate[] p = new Predicate[list.size()];  
            return cb.and(list.toArray(p)); 
			}
		}, pageable);
	}

	public Long findUserNumber(WechatUserVo uvo) {
		return wechatUserReadRepository.count(new Specification<WechatUser>() {
			@Override
			public Predicate toPredicate(Root<WechatUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if(!StringUtils.isNull(uvo.getOpenId())) {
					list.add(cb.equal(root.get("openId").as(String.class), uvo.getOpenId()));
				}
			Predicate[] p = new Predicate[list.size()];  
            return cb.and(list.toArray(p)); 
			}
		});
	}

}
