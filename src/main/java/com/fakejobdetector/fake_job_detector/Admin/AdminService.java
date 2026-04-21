package com.fakejobdetector.fake_job_detector.Admin;

import com.fakejobdetector.fake_job_detector.Entities.BlacklistedCompany;
import com.fakejobdetector.fake_job_detector.Entities.User;
import com.fakejobdetector.fake_job_detector.Report.JobReport;
import com.fakejobdetector.fake_job_detector.Repository.BlacklistedCompanyRepo;
import com.fakejobdetector.fake_job_detector.Repository.JobReportRepo;
import com.fakejobdetector.fake_job_detector.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepo userRepo;
    private final JobReportRepo jobReportRepo;
    private final BlacklistedCompanyRepo blacklistedCompanyRepo;

    // Sab users dekho
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // Sab reports dekho
    public List<JobReport> getAllReports() {
        return jobReportRepo.findAllByOrderByCreatedAtDesc();
    }

    // Company blacklist mein add karo
    public String addToBlacklist(String companyName) {

        // Already blacklisted hai?
        if (blacklistedCompanyRepo
                .existsByCompanyNameIgnoreCase(companyName)) {
            return companyName + " is already blacklisted!";
        }

        BlacklistedCompany company = BlacklistedCompany.builder()
                .companyName(companyName)
                .reportCount(0)
                .build();

        blacklistedCompanyRepo.save(company);
        return companyName + " added to blacklist successfully!";
    }

    // Company blacklist se remove karo
    public String removeFromBlacklist(Long id) {
        BlacklistedCompany company = blacklistedCompanyRepo
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Company not found!"));

        blacklistedCompanyRepo.delete(company);
        return company.getCompanyName() +
                " removed from blacklist!";
    }

    // Sab blacklisted companies dekho
    public List<BlacklistedCompany> getBlacklist() {
        return blacklistedCompanyRepo
                .findAllByOrderByReportCountDesc();
    }
}