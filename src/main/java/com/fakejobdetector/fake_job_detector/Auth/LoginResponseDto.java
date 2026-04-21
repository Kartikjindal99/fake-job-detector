package com.fakejobdetector.fake_job_detector.Auth;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String name;
    private String email;
    private String token;
    private String role;
}