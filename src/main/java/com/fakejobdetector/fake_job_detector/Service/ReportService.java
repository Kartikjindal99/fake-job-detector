package com.fakejobdetector.fake_job_detector.Service;

import com.fakejobdetector.fake_job_detector.Entities.BlacklistedCompany;
import com.fakejobdetector.fake_job_detector.Entities.JobAnalysis;
import com.fakejobdetector.fake_job_detector.Entities.User;
import com.fakejobdetector.fake_job_detector.Report.JobReport;
import com.fakejobdetector.fake_job_detector.Report.ReportRequest;
import com.fakejobdetector.fake_job_detector.Repository.BlacklistedCompanyRepo;
import com.fakejobdetector.fake_job_detector.Repository.JobAnalysisRepo;
import com.fakejobdetector.fake_job_detector.Repository.JobReportRepo;
import com.fakejobdetector.fake_job_detector.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final JobReportRepo jobReportRepo;
    private final JobAnalysisRepo jobAnalysisRepo;
    private final UserRepo userRepo;
    private final BlacklistedCompanyRepo blacklistedCompanyRepo;

    //Report a job
    public String report(ReportRequest request){
        String email= SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        User user=userRepo.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User Not Found"));
        //Analysis dhudho
        JobAnalysis analysis=jobAnalysisRepo.findById(request.getAnalysisId())
                .orElseThrow(()->new RuntimeException("Analysis not Found!"));

        //Report Banao aur save karo
        JobReport report=JobReport.builder()
                .user(user)
                .jobAnalysis(analysis)
                .reason(request.getReason())
                .build();
        jobReportRepo.save(report);

        // Company ka report count badhao
        // Agar company exist karti hai blacklist mein
        String companyName = analysis.getCompanyName();
        if (companyName != null && !companyName.trim().isEmpty()) {
            BlacklistedCompany company = blacklistedCompanyRepo
                    .findByCompanyNameIgnoreCase(companyName)
                    .orElse(BlacklistedCompany.builder()
                            .companyName(companyName)
                            .reportCount(0)
                            .build());

            company.setReportCount(company.getReportCount() + 1);
            blacklistedCompanyRepo.save(company);
        }

        return "Job reported successfully!";
    }

    // Leaderboard — most reported companies
    public List<BlacklistedCompany> getLeaderboard() {
        return blacklistedCompanyRepo
                .findAllByOrderByReportCountDesc();
    }

}
