package com.hiresense.dto;
import java.util.List;

public class JobDashboardDTO {

    private Long jobId;
    private int totalCandidates;
    private double averageMatchScore;
    private List<DashboardCandidateDTO> topCandidates;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public int getTotalCandidates() {
        return totalCandidates;
    }

    public void setTotalCandidates(int totalCandidates) {
        this.totalCandidates = totalCandidates;
    }

    public double getAverageMatchScore() {
        return averageMatchScore;
    }

    public void setAverageMatchScore(double averageMatchScore) {
        this.averageMatchScore = averageMatchScore;
    }

    public List<DashboardCandidateDTO> getTopCandidates() {
        return topCandidates;
    }

    public void setTopCandidates(List<DashboardCandidateDTO> topCandidates) {
        this.topCandidates = topCandidates;
    }
}
