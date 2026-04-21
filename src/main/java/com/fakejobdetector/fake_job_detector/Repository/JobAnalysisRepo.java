package com.fakejobdetector.fake_job_detector.Repository;
import com.fakejobdetector.fake_job_detector.Entities.JobAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobAnalysisRepo
        extends JpaRepository<JobAnalysis, Long> {
    List<JobAnalysis> findByUserIdOrderByCreatedAtDesc(Long userId);
}
