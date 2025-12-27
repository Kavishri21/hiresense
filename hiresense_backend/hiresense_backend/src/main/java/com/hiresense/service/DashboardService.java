package com.hiresense.service;
import com.hiresense.dto.JobDashboardDTO;
import com.hiresense.model.MatchResult;
import com.hiresense.repository.MatchResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final MatchResultRepository matchResultRepository;

    public DashboardService(MatchResultRepository matchResultRepository) {
        this.matchResultRepository = matchResultRepository;
    }

    public JobDashboardDTO getJobDashboard(Long jobId) {

        List<MatchResult> results =
                matchResultRepository.findByJobIdOrderByMatchScoreDesc(jobId);

        JobDashboardDTO dto = new JobDashboardDTO();
        dto.setJobId(jobId);
        dto.setTotalCandidates(results.size());

        if (!results.isEmpty()) {
            double avgScore = results.stream()
                    .mapToDouble(MatchResult::getMatchScore)
                    .average()
                    .orElse(0.0);

            dto.setAverageMatchScore(avgScore);
            dto.setTopCandidates(
                    results.stream().limit(5).toList()
            );
        }

        return dto;
    }
}
