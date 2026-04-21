package com.fakejobdetector.fake_job_detector.Repository;

import com.fakejobdetector.fake_job_detector.Report.JobReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobReportRepo extends JpaRepository<JobReport,Long> {
    List<JobReport>findAllByOrderByCreatedAtDesc();
}
