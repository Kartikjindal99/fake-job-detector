package com.fakejobdetector.fake_job_detector.Repository;

import com.fakejobdetector.fake_job_detector.Entities.BlacklistedCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BlacklistedCompanyRepo
        extends JpaRepository<BlacklistedCompany, Long> {

    boolean existsByCompanyNameIgnoreCase(String companyName);

    // Report service mein use hoga
    Optional<BlacklistedCompany> findByCompanyNameIgnoreCase(
            String companyName);

    // Leaderboard — most reported pehle
    List<BlacklistedCompany> findAllByOrderByReportCountDesc();
}