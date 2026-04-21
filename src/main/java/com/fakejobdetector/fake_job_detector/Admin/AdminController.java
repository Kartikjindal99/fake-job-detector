package com.fakejobdetector.fake_job_detector.Admin;

import com.fakejobdetector.fake_job_detector.Entities.BlacklistedCompany;
import com.fakejobdetector.fake_job_detector.Entities.User;
import com.fakejobdetector.fake_job_detector.Report.JobReport;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // Sab users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    // Sab reports
    @GetMapping("/reports")
    public ResponseEntity<List<JobReport>> getAllReports() {
        return ResponseEntity.ok(adminService.getAllReports());
    }

    // Blacklist mein add karo
    @PostMapping("/blacklist")
    public ResponseEntity<String> addToBlacklist(
            @RequestBody BlacklistRequest request) {
        String result = adminService
                .addToBlacklist(request.getCompanyName());
        return ResponseEntity.ok(result);
    }

    // Blacklist se remove karo
    @DeleteMapping("/blacklist/{id}")
    public ResponseEntity<String> removeFromBlacklist(
            @PathVariable Long id) {
        String result = adminService.removeFromBlacklist(id);
        return ResponseEntity.ok(result);
    }

    // Sab blacklisted companies
    @GetMapping("/blacklist")
    public ResponseEntity<List<BlacklistedCompany>> getBlacklist() {
        return ResponseEntity.ok(adminService.getBlacklist());
    }
}