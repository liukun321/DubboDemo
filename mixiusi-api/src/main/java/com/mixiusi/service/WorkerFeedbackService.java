package com.mixiusi.service;

import java.util.List;

import com.mixiusi.bean.WorkerFeedback;
import com.mixiusi.bean.vo.WorkerFeedbackVo;

public interface WorkerFeedbackService {
	
	public List<WorkerFeedback> findAll(WorkerFeedbackVo uvo);
	
	Long queryFeedBackSum(WorkerFeedbackVo uvo);
}
