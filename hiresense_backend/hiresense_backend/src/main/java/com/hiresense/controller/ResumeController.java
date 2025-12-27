package com.hiresense.controller;
import com.hiresense.model.Candidate;
import com.hiresense.service.CandidateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.hiresense.model.Skill;
import com.hiresense.model.CandidateSkill;
import com.hiresense.repository.SkillRepository;
import com.hiresense.repository.CandidateSkillRepository;
import com.hiresense.util.ResumeParserUtil;
import com.hiresense.util.SkillExtractorUtil;
import java.util.List;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final CandidateService candidateService;

    private final SkillRepository skillRepository;
    private final CandidateSkillRepository candidateSkillRepository;

    public ResumeController(
            CandidateService candidateService,
            SkillRepository skillRepository,
            CandidateSkillRepository candidateSkillRepository
    ) {
        this.candidateService = candidateService;
        this.skillRepository = skillRepository;
        this.candidateSkillRepository = candidateSkillRepository;
    }

    

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("education") String education,
            @RequestParam("experience") int experience
    ) throws IOException {

        String uploadDir = System.getProperty("user.dir") + "/uploads";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = uploadDir + "/" + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        Candidate candidate = new Candidate();
        candidate.setName(name);
        candidate.setEmail(email);
        candidate.setEducation(education);
        candidate.setExperienceYears(experience);
        candidate.setResumePath(filePath);

        Candidate savedCandidate = candidateService.saveCandidate(candidate);

        String resumeText = ResumeParserUtil.extractText(new File(filePath));
        List<String> extractedSkills = SkillExtractorUtil.extractSkills(resumeText);

        for (String skillName : extractedSkills) {
            Skill skill = skillRepository.findByName(skillName);
            if (skill == null) {
                skill = new Skill(skillName);
                skill = skillRepository.save(skill);
            }

            CandidateSkill cs = new CandidateSkill(savedCandidate, skill);
            candidateSkillRepository.save(cs);
        }


        return ResponseEntity.ok("Resume uploaded successfully");
    }
}