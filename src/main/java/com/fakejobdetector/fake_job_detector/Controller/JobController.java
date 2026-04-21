package com.fakejobdetector.fake_job_detector.Controller;

import com.fakejobdetector.fake_job_detector.Entities.JobAnalysis;
import com.fakejobdetector.fake_job_detector.Job.JobAnalyzeRequest;
import com.fakejobdetector.fake_job_detector.Job.JobAnalyzeResponse;
import com.fakejobdetector.fake_job_detector.Service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    // Analyze a job
    @PostMapping("/analyze")
    public ResponseEntity<JobAnalyzeResponse> analyze(
            @RequestBody JobAnalyzeRequest request) {
        JobAnalyzeResponse response = jobService.analyze(request);
        return ResponseEntity.ok(response);
    }

    // Get history
    @GetMapping("/history")
    public ResponseEntity<List<JobAnalysis>> getHistory() {
        return ResponseEntity.ok(jobService.getHistory());
    }
}