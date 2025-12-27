package com.hiresense.service;
import com.hiresense.dto.DashboardCandidateDTO;
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

            List<DashboardCandidateDTO> topCandidates =
                    results.stream()
                            .limit(5)
                            .map(r -> new DashboardCandidateDTO(
                                    r.getCandidate().getId(),
                                    r.getCandidate().getName(),
                                    r.getCandidate().getEmail(),
                                    r.getMatchScore(),
                                    r.getSummary()
                            ))
                            .toList();

            dto.setTopCandidates(topCandidates);
        }

        return dto;
    }
}
