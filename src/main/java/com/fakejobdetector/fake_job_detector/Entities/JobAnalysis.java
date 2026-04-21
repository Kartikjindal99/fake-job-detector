package com.fakejobdetector.fake_job_detector.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="job_analyses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class JobAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;
    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "company_name")
    private String companyName;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String salary;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "fake_score")
    private int fakeScore;

    private String verdict;

    @Column(columnDefinition = "TEXT")
    private String flags;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
