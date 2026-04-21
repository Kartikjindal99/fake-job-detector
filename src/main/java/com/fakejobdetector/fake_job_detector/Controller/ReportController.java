package com.fakejobdetector.fake_job_detector.Controller;

import com.fakejobdetector.fake_job_detector.Entities.BlacklistedCompany;
import com.fakejobdetector.fake_job_detector.Report.ReportRequest;
import com.fakejobdetector.fake_job_detector.Service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // Report a job
    @PostMapping
    public ResponseEntity<String> report(
            @RequestBody ReportRequest request) {
        String result = reportService.report(request);
        return ResponseEntity.ok(result);
    }

    // Public leaderboard
    @GetMapping("/leaderboard")
    public ResponseEntity<List<BlacklistedCompany>> leaderboard() {
        return ResponseEntity.ok(
                reportService.getLeaderboard());
    }
}