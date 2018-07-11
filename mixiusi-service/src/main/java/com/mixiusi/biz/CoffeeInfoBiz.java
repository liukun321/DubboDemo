package com.mixiusi.biz;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mixiusi.bean.CoffeeInfo;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.bean.vo.CoffeeVo;
import com.mixiusi.repository.read.CoffeeInfoReadRepository;
import com.mixiusi.repository.write.CoffeeInfoRepository;
@Transactional
@Service
public class CoffeeInfoBiz {
	@Autowired
	private CoffeeInfoReadRepository coffeeInfoReadRepository;
	@Autowired
	private CoffeeInfoRepository coffeeInfoRepository;
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	/**
	 * 查询所有咖啡信息
	 */
	public Page<CoffeeInfo> queryAllCoffeeInfo(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "coffeeId");
		return coffeeInfoReadRepository.findAll(pageable);
	}


//	@Override
//	public Double queryCoffeeInfoForPrice(Integer coffeeId) {
//		CoffeeInfo coffeeInfo = coffeeInfoRepository.findByCoffeeId(coffeeId);
//		if(null != coffeeInfo) {
//			return coffeeInfo.getPrice();
//		}
//		return null;
//	}

	/**
	 * 添加咖啡品种
	 */
	public CoffeeInfo addCoffeeInfo(CoffeeInfo coffeeInfo) {
		return coffeeInfoRepository.save(coffeeInfo);
		
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean removeCoffeeInfo(List<String> coffeeIds) {
		for(String id : coffeeIds) {
			CoffeeInfo coffee = coffeeInfoRepository.findOne(id);
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

	public CoffeeInfo queryCoffeeInfoById(String coffeeId) {
		return coffeeInfoReadRepository.findOne(coffeeId);
	}

	public Long queryCoffeeInfoNumber() {
		return coffeeInfoReadRepository.count();
	}

	public Long queryCoffeeInfoSum(CoffeeVo cvo) {
		 return coffeeInfoReadRepository.count(new Specification<CoffeeInfo>(){  
				@Override
				public Predicate toPredicate(Root<CoffeeInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					 List<Predicate> list = new ArrayList<>();
					 if(!StringUtils.isNull(cvo.getCoffeeId())){  
						 list.add(cb.equal(root.get("coffeeId").as(String.class), cvo.getCoffeeId()));
		             }
					 Predicate[] p = new Predicate[list.size()];  
		             return cb.and(list.toArray(p)); 
				}  
	        }); 
	}

	public List<CoffeeInfo> queryCoffeeInfo(CoffeeVo cvo) {
		Integer page = cvo.getPage();
		Integer size = cvo.getSize();
		String sort = cvo.getSort();
		Pageable pageable = null;
		if(StringUtils.isNull(sort)) {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, "coffeeId");
		}else {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, sort);
		}
        return coffeeInfoReadRepository.findAll(new Specification<CoffeeInfo>(){  
			@Override
			public Predicate toPredicate(Root<CoffeeInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> list = new ArrayList<>();
				 if(!StringUtils.isNull(cvo.getCoffeeId())){  
					 list.add(cb.equal(root.get("coffeeId").as(String.class), cvo.getCoffeeId()));
	             }
				 Predicate[] p = new Predicate[list.size()];  
	             return cb.and(list.toArray(p)); 
			}  
        }, pageable).getContent(); 
	}
}
