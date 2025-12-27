package com.hiresense.controller;
import com.hiresense.repository.MatchResultRepository;
import com.hiresense.service.MatchingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/match")
public class MatchController {

    private final MatchingService matchingService;
    private final MatchResultRepository matchResultRepository;

    public MatchController(
            MatchingService matchingService,
            MatchResultRepository matchResultRepository
    ) {
        this.matchingService = matchingService;
        this.matchResultRepository = matchResultRepository;
    }

    @PostMapping("/job/{jobId}")
    public ResponseEntity<String> matchJob(@PathVariable Long jobId) {
        matchingService.matchCandidatesToJob(jobId);
        return ResponseEntity.ok("Matching completed");
    }

    @GetMapping("/results/{jobId}")
    public ResponseEntity<?> getResults(@PathVariable Long jobId) {
        return ResponseEntity.ok(
                matchResultRepository.findByJobIdOrderByMatchScoreDesc(jobId)
        );
    }
}
