package com.fakejobdetector.fake_job_detector.Scoring;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScoringResult {
    private int score;//0-100
    private String verdict;//ligit,suspicious,fake
    private List<String>flags;
}
