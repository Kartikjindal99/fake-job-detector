package com.fakejobdetector.fake_job_detector.Job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobAnalyzeResponse {
    private Long id;
    private String jobTitle;
    private String companyName;
    private int fakeScore;
    private String verdict;
    private List<String>flags;
}
