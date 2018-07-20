package com.mixiusi.repository.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.WorkerFeedback;

@Repository
public interface WorkerFeedbackRepository extends JpaRepository<WorkerFeedback, String>, JpaSpecificationExecutor<WorkerFeedback>{

}
