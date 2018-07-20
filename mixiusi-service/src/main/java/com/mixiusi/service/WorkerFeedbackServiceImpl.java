package com.mixiusi.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.WorkerFeedback;
import com.mixiusi.bean.vo.WorkerFeedbackVo;
import com.mixiusi.biz.WorkerFeedbackBiz;

@Service(interfaceClass = WorkerFeedbackService.class)
public class WorkerFeedbackServiceImpl implements WorkerFeedbackService {
	private final Logger log = LoggerFactory.getLogger(WorkerFeedbackServiceImpl.class);
	@Autowired
	private WorkerFeedbackBiz workerFeedbackBiz;
	
	@Override
	public List<WorkerFeedback> findAll(WorkerFeedbackVo uvo) {
		return workerFeedbackBiz.findAll(uvo).getContent();
	}

	@Override
	public Long queryFeedBackSum(WorkerFeedbackVo uvo) {
		return workerFeedbackBiz.queryFeedBackSum(uvo);
	}

}
