package com.fakejobdetector.fake_job_detector.Auth;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String name;
    private String email;
    private String password;
}