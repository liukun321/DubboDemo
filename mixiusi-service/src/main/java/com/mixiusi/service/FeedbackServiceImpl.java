package com.mixiusi.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.WorkerFeedback;
import com.mixiusi.biz.FeedbackBiz;

@Service(interfaceClass = FeedbackService.class)
public class FeedbackServiceImpl implements FeedbackService {
	@Autowired
	private FeedbackBiz feedbackBiz;

	@Override
	public WorkerFeedback addFeedback(WorkerFeedback back) {
		return feedbackBiz.addFeedback(back);
	}

}
