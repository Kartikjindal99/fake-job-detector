package com.fakejobdetector.fake_job_detector.Auth;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}