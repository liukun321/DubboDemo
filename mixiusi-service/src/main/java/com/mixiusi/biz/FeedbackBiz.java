package com.mixiusi.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mixiusi.bean.WorkerFeedback;
import com.mixiusi.repository.write.FeedbackRepository;

@Service
@Transactional
public class FeedbackBiz {
	@Autowired
	private FeedbackRepository feedbackRepository;
	
	public WorkerFeedback addFeedback(WorkerFeedback back) {
		return feedbackRepository.save(back);
	}

}
