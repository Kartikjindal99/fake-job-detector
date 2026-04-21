package com.fakejobdetector.fake_job_detector.Report;

import com.fakejobdetector.fake_job_detector.Entities.JobAnalysis;
import com.fakejobdetector.fake_job_detector.Entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="analysis_id",nullable = false)
    private JobAnalysis jobAnalysis;

    @Column(columnDefinition = "TEXT")
    private String reason;
    @Column(name="created_at")
    private LocalDateTime createdAt;
    @PrePersist
    public void prePersist(){
        this.createdAt=LocalDateTime.now();
    }
}
