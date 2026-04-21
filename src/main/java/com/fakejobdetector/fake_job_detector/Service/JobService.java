package com.fakejobdetector.fake_job_detector.Service;

import com.fakejobdetector.fake_job_detector.Entities.JobAnalysis;
import com.fakejobdetector.fake_job_detector.Entities.User;
import com.fakejobdetector.fake_job_detector.Job.JobAnalyzeRequest;
import com.fakejobdetector.fake_job_detector.Job.JobAnalyzeResponse;
import com.fakejobdetector.fake_job_detector.Repository.JobAnalysisRepo;
import com.fakejobdetector.fake_job_detector.Repository.UserRepo;
import com.fakejobdetector.fake_job_detector.Scoring.ScoringEngine;
import com.fakejobdetector.fake_job_detector.Scoring.ScoringResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobAnalysisRepo jobAnalysisRepo;
    private final UserRepo userRepo;
    private final ScoringEngine scoringEngine;

    public JobAnalyzeResponse analyze(JobAnalyzeRequest request) {

        // Current logged in user ka email nikalo
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        // DB se user nikalo
        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found!"));

        // Scoring engine se score nikalo
        ScoringResult result = scoringEngine.analyze(
                request.getJobTitle(),
                request.getCompanyName(),
                request.getDescription(),
                request.getSalary(),
                request.getContactEmail()
        );

        // Flags list ko string mein convert karo
        String flagsString = String.join(", ", result.getFlags());

        // DB mein save karo
        JobAnalysis analysis = JobAnalysis.builder()
                .user(user)
                .jobTitle(request.getJobTitle())
                .companyName(request.getCompanyName())
                .description(request.getDescription())
                .salary(request.getSalary())
                .contactEmail(request.getContactEmail())
                .fakeScore(result.getScore())
                .verdict(result.getVerdict())
                .flags(flagsString)
                .build();

        JobAnalysis saved = jobAnalysisRepo.save(analysis);

        // Response return karo
        return JobAnalyzeResponse.builder()
                .id(saved.getId())
                .jobTitle(saved.getJobTitle())
                .companyName(saved.getCompanyName())
                .fakeScore(saved.getFakeScore())
                .verdict(saved.getVerdict())
                .flags(result.getFlags())
                .build();
    }

    // User ki history
    public List<JobAnalysis> getHistory() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found!"));

        return jobAnalysisRepo
                .findByUserIdOrderByCreatedAtDesc(user.getId());
    }
}
