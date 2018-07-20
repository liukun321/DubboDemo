package com.mixiusi.biz;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mixiusi.repository.read.MobileCoffeelistReadRepository;
import com.mixiusi.repository.read.MobilePayReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mixiusi.bean.MobileCoffeeList;
import com.mixiusi.bean.MobilePay;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.bean.vo.MobilePayDetailVo;
import com.mixiusi.repository.write.MobileCoffeelistRepository;
import com.mixiusi.repository.write.MobilePayRepository;

@Service
public class MobileCoffeelistBiz {
	@Autowired
	private MobileCoffeelistRepository mobilecoffeelistRepository;
	@Autowired
	private MobileCoffeelistReadRepository mobileCoffeelistReadRepository;
	@Autowired
	private MobilePayRepository mobilePayRepository;
	@Autowired
	private MobilePayReadRepository mobilePayReadRepository;
	
	public List<MobileCoffeeList> queryCoffeeAll(String indentId) {
		return mobileCoffeelistReadRepository.findByMid(indentId);
	}
	
	public Page<MobileCoffeeList> queryMobilePayDetail(MobilePayDetailVo mpdvo) {
		Integer page = mpdvo.getPage();
		Integer size = mpdvo.getSize();
		String sort = mpdvo.getSort();
		Pageable pageable = null;
		if(StringUtils.isNull(sort)) {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");
		}else {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, sort);
		}
		return mobileCoffeelistReadRepository.findAll(new Specification<MobileCoffeeList>() {
			@Override
			public Predicate toPredicate(Root<MobileCoffeeList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				
				if(!StringUtils.isNull(mpdvo.getIndentId())) {
					list.add(cb.equal(root.get("mid").as(MobilePay.class), mobilePayReadRepository.findOne(mpdvo.getIndentId())));
				}
			Predicate[] p = new Predicate[list.size()];  
            return cb.and(list.toArray(p)); 
			}
		}, pageable);
	}
	
	public Long queryDetailSumNumber(MobilePayDetailVo mpdvo) {
		return mobileCoffeelistReadRepository.count(new Specification<MobileCoffeeList>() {
			@Override
			public Predicate toPredicate(Root<MobileCoffeeList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				
				if(!StringUtils.isNull(mpdvo.getIndentId())) {
					list.add(cb.equal(root.get("mid").as(MobilePay.class), mobilePayRepository.findOne(mpdvo.getIndentId())));
				}
			Predicate[] p = new Predicate[list.size()];  
            return cb.and(list.toArray(p)); 
			}
		});
	}

}
