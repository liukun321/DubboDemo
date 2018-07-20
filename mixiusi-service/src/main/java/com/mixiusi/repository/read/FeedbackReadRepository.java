package com.mixiusi.repository.read;

import com.mixiusi.bean.WorkerFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackReadRepository extends JpaRepository<WorkerFeedback, Integer> {

}
