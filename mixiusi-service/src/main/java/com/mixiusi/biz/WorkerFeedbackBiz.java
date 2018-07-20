package com.mixiusi.biz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mixiusi.repository.read.WorkerFeedbackReadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mixiusi.bean.WorkerFeedback;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.bean.vo.WorkerFeedbackVo;
import com.mixiusi.repository.write.WorkerFeedbackRepository;
@Transactional
@Service
public class WorkerFeedbackBiz {
	private final Logger log = LoggerFactory.getLogger(WorkerFeedbackBiz.class);
	@Autowired
	private WorkerFeedbackReadRepository workerFeedbackReadRepository;
	
	public Page<WorkerFeedback> findAll(WorkerFeedbackVo uvo) {
		Integer page = uvo.getPage();
		Integer size = uvo.getSize();
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Pageable pageable = null;
		if(StringUtils.isNull(uvo.getSort())) {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, "workerId");
		}else {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, uvo.getSort());
		}
		return workerFeedbackReadRepository.findAll(new Specification<WorkerFeedback>() {
			@Override
			public Predicate toPredicate(Root<WorkerFeedback> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				
				if(!StringUtils.isNull(uvo.getWorkerId())) {
					list.add(cb.equal(root.get("workerId").as(String.class), uvo.getWorkerId()));
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

	public Long queryFeedBackSum(WorkerFeedbackVo uvo) {
		
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return workerFeedbackReadRepository.count(new Specification<WorkerFeedback>() {
			@Override
			public Predicate toPredicate(Root<WorkerFeedback> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				
				if(!StringUtils.isNull(uvo.getWorkerId())) {
					list.add(cb.equal(root.get("workerId").as(String.class), uvo.getWorkerId()));
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

}
