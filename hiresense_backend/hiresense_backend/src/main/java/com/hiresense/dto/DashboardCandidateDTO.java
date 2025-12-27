package com.hiresense.dto;
public class DashboardCandidateDTO {

    private Long candidateId;
    private String name;
    private String email;
    private double matchScore;
    private String summary;

    public DashboardCandidateDTO(
            Long candidateId,
            String name,
            String email,
            double matchScore,
            String summary
    ) {
        this.candidateId = candidateId;
        this.name = name;
        this.email = email;
        this.matchScore = matchScore;
        this.summary = summary;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getMatchScore() {
        return matchScore;
    }

    public String getSummary() {
        return summary;
    }
}
