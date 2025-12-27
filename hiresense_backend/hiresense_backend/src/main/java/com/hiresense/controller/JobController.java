package com.hiresense.controller;
import com.hiresense.model.Job;
import com.hiresense.model.Recruiter;
import com.hiresense.repository.JobRepository;
import com.hiresense.repository.RecruiterRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobRepository jobRepository;
    private final RecruiterRepository recruiterRepository;

    public JobController(
            JobRepository jobRepository,
            RecruiterRepository recruiterRepository
    ) {
        this.jobRepository = jobRepository;
        this.recruiterRepository = recruiterRepository;
    }

    @PostMapping("/{recruiterId}")
    public ResponseEntity<Job> createJob(
            @PathVariable Long recruiterId,
            @RequestBody Job job
    ) {
        Recruiter recruiter =
                recruiterRepository.findById(recruiterId).orElseThrow();

        job.setRecruiter(recruiter);
        return ResponseEntity.ok(jobRepository.save(job));
    }
}