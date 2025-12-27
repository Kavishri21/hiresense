package com.hiresense.controller;
import com.hiresense.model.Recruiter;
import com.hiresense.repository.RecruiterRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruiters")
public class RecruiterController {

    private final RecruiterRepository recruiterRepository;

    public RecruiterController(RecruiterRepository recruiterRepository) {
        this.recruiterRepository = recruiterRepository;
    }

    @PostMapping
    public ResponseEntity<Recruiter> createRecruiter(
            @RequestBody Recruiter recruiter
    ) {
        return ResponseEntity.ok(
                recruiterRepository.save(recruiter)
        );
    }
}
