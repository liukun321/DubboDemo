package com.mixiusi.repository.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.WorkerFeedback;

@Repository
public interface FeedbackRepository extends JpaRepository<WorkerFeedback, Integer> {

}
