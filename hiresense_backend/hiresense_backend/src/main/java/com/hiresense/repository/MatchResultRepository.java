package com.hiresense.repository;
import com.hiresense.model.MatchResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchResultRepository extends JpaRepository<MatchResult, Long> {

    List<MatchResult> findByJobIdOrderByMatchScoreDesc(Long jobId);
}
