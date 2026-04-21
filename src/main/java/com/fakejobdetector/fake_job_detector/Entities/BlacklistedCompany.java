package com.fakejobdetector.fake_job_detector.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="blacklisted_companies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlacklistedCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="company_name",nullable = false,unique = true)
    private String companyName;

    @Column(name="report_count")
    private int reportCount=0;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist(){
        this.createdAt= LocalDateTime.now();
    }
}
