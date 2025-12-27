package com.hiresense.controller;
import com.hiresense.dto.JobDashboardDTO;
import com.hiresense.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<JobDashboardDTO> getJobDashboard(
            @PathVariable Long jobId
    ) {
        return ResponseEntity.ok(
                dashboardService.getJobDashboard(jobId)
        );
    }
}
