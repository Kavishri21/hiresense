package com.hiresense.service;

import com.hiresense.model.*;
import com.hiresense.repository.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MatchingService {

    private final JobRepository jobRepository;
    private final CandidateRepository candidateRepository;
    private final CandidateSkillRepository candidateSkillRepository;
    private final MatchResultRepository matchResultRepository;

    public MatchingService(
            JobRepository jobRepository,
            CandidateRepository candidateRepository,
            CandidateSkillRepository candidateSkillRepository,
            MatchResultRepository matchResultRepository
    ) {
        this.jobRepository = jobRepository;
        this.candidateRepository = candidateRepository;
        this.candidateSkillRepository = candidateSkillRepository;
        this.matchResultRepository = matchResultRepository;
    }

    public void matchCandidatesToJob(Long jobId) {

        Job job = jobRepository.findById(jobId).orElseThrow();
        List<Candidate> candidates = candidateRepository.findAll();

        Set<String> jobSkills = parseJobSkills(job.getRequiredSkills());

        for (Candidate candidate : candidates) {

            List<CandidateSkill> candidateSkills =
                    candidateSkillRepository.findByCandidateId(candidate.getId());

            Set<String> candidateSkillSet = new HashSet<>();
            for (CandidateSkill cs : candidateSkills) {
                candidateSkillSet.add(cs.getSkill().getName());
            }

            double skillScore = calculateSkillScore(jobSkills, candidateSkillSet);
            double experienceScore = calculateExperienceScore(
                    job.getMinExperience(), candidate.getExperienceYears());

            double finalScore = (skillScore * 0.7) + (experienceScore * 0.3);

            MatchResult result = new MatchResult();
            result.setJob(job);
            result.setCandidate(candidate);
            result.setMatchScore(finalScore);
            result.setSummary(generateSummary(candidateSkillSet, jobSkills));

            matchResultRepository.save(result);
        }
    }

    private Set<String> parseJobSkills(String skills) {
        Set<String> set = new HashSet<>();

        if (skills == null || skills.trim().isEmpty()) {
            return set;
        }

        for (String skill : skills.split(",")) {
            set.add(skill.trim().toLowerCase());
        }
        return set;
    }

    private double calculateSkillScore(Set<String> jobSkills, Set<String> candidateSkills) {
        int matched = 0;
        for (String skill : jobSkills) {
            if (candidateSkills.contains(skill)) {
                matched++;
            }
        }
        return jobSkills.isEmpty() ? 0 : ((double) matched / jobSkills.size()) * 100;
    }

    private double calculateExperienceScore(int required, int actual) {
        if (required <= 0) {
            return 100;
        }
        if (actual >= required) {
            return 100;
        }
        return ((double) actual / required) * 100;
    }

    private String generateSummary(Set<String> candidateSkills, Set<String> jobSkills) {
        Set<String> matched = new HashSet<>(candidateSkills);
        matched.retainAll(jobSkills);
        return "Matched skills: " + matched;
    }
}
