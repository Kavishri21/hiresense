package com.hiresense.controller;
import com.hiresense.model.Candidate;
import com.hiresense.service.CandidateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final CandidateService candidateService;

    public ResumeController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("education") String education,
            @RequestParam("experience") int experience
    ) throws IOException {

        String uploadDir = "uploads/";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdir();
        }

        String filePath = uploadDir + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        Candidate candidate = new Candidate();
        candidate.setName(name);
        candidate.setEmail(email);
        candidate.setEducation(education);
        candidate.setExperienceYears(experience);
        candidate.setResumePath(filePath);

        candidateService.saveCandidate(candidate);

        return ResponseEntity.ok("Resume uploaded successfully");
    }
}