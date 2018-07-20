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
import javax.transaction.Transactional;

import com.mixiusi.repository.read.PickupReadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mixiusi.bean.Pickup;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.bean.vo.PickupVo;
import com.mixiusi.repository.write.PickupRepository;
@Transactional
@Service
public class PickupBiz {
	private final Logger log = LoggerFactory.getLogger(PickupBiz.class);
	@Autowired
	private PickupRepository pickupRepository;
	@Autowired
	private PickupReadRepository pickupReadRepository;
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	
	public Pickup addPickup(Pickup pk) {
		return pickupRepository.save(pk);
	}

	public Pickup queryPickup(String pkCode) {
		return pickupReadRepository.findByPickupCodeAndIsUse(pkCode, false);
	}

	public Pickup queryPickupById(String indentId) {
		return pickupReadRepository.findOne(indentId);
	}

	public void updateStatus(Pickup pk) {
		pickupRepository.save(pk);
		
	}
	
	public Page<Pickup> getAllPickup(PickupVo uvo) {
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info("canshu:" + uvo + "---" + uvo.getPage() + "---" + uvo.getSize());
		Pageable pageable = new PageRequest(uvo.getPage(), uvo.getSize(), Sort.Direction.DESC, "indentId");
        return pickupReadRepository.findAll(new Specification<Pickup>(){
			@Override
			public Predicate toPredicate(Root<Pickup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> list = new ArrayList<>();
				 if(!StringUtils.isNull(uvo.getIndentId())){  
					 list.add(cb.equal(root.get("indentId").as(String.class), uvo.getIndentId()));
	             }
				 //判断错误的持续时间是否超过给定的值
				 if(!StringUtils.isNull(uvo.getOpenId())){  
					 list.add(cb.equal(root.get("openId").as(String.class), uvo.getOpenId()));
	             }
				 if(!StringUtils.isNull(uvo.getPickupCode())){  
					 list.add(cb.equal(root.get("pickupCode").as(String.class), uvo.getPickupCode()));
	             }
				 Date startTime = uvo.getStartTime();
				 Date endTime = uvo.getEndTime();
				 if(!StringUtils.isNull(startTime) || !StringUtils.isNull(endTime)) {
					 try {
						 if(StringUtils.isNull(startTime)) {
							 list.add(cb.lessThan(root.get("createTime").as(Date.class),
										sdfmat.parse(sdfmat.format(uvo.getEndTime()))));
						 }else if(StringUtils.isNull(endTime)) {
							 list.add(cb.greaterThan(root.get("createTime").as(Date.class), 
										sdfmat.parse(sdfmat.format(uvo.getStartTime()))));
						 }else {
							 list.add(cb.between(root.get("createTime").as(Date.class), 
									 sdfmat.parse(sdfmat.format(uvo.getStartTime())),
									 sdfmat.parse(sdfmat.format(uvo.getEndTime()))));
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

	public Long getAllPickupNumber(PickupVo uvo) {
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return pickupReadRepository.count(new Specification<Pickup>(){
			@Override
			public Predicate toPredicate(Root<Pickup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> list = new ArrayList<>();
				 if(!StringUtils.isNull(uvo.getIndentId())){  
					 list.add(cb.equal(root.get("indentId").as(String.class), uvo.getIndentId()));
	             }
				 //判断错误的持续时间是否超过给定的值
				 if(!StringUtils.isNull(uvo.getOpenId())){  
					 list.add(cb.equal(root.get("openId").as(String.class), uvo.getOpenId()));
	             }
				 if(!StringUtils.isNull(uvo.getPickupCode())){  
					 list.add(cb.equal(root.get("pickupCode").as(String.class), uvo.getPickupCode()));
	             }
				 Date startTime = uvo.getStartTime();
				 Date endTime = uvo.getEndTime();
				 if(!StringUtils.isNull(startTime) || !StringUtils.isNull(endTime)) {
					 try {
						 if(StringUtils.isNull(startTime)) {
							 list.add(cb.lessThan(root.get("createTime").as(Date.class),
										sdfmat.parse(sdfmat.format(uvo.getEndTime()))));
						 }else if(StringUtils.isNull(endTime)) {
							 list.add(cb.greaterThan(root.get("createTime").as(Date.class), 
										sdfmat.parse(sdfmat.format(uvo.getStartTime()))));
						 }else {
							 list.add(cb.between(root.get("createTime").as(Date.class), 
									 sdfmat.parse(sdfmat.format(uvo.getStartTime())),
									 sdfmat.parse(sdfmat.format(uvo.getEndTime()))));
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
	@Transactional
	public Boolean removePickup(List<String> indentIds) {
		for (String id : indentIds) {
			Pickup p = pickupRepository.findOne(id);
			if(null != p) {
				entityManager.remove(p);
			}else {
				return false;
			}
		}
		entityManager.flush();
		entityManager.clear();
		return true;
	}

}
