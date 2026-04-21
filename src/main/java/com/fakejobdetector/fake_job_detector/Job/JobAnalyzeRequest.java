package com.fakejobdetector.fake_job_detector.Job;

import lombok.Data;

@Data
public class JobAnalyzeRequest {
    private String jobTitle;
    private String companyName;
    private String description;
    private String salary;
    private String contactEmail;
}
