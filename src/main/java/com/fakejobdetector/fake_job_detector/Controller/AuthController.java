package com.fakejobdetector.fake_job_detector.Controller;

import com.fakejobdetector.fake_job_detector.Auth.LoginRequestDto;
import com.fakejobdetector.fake_job_detector.Auth.LoginResponseDto;
import com.fakejobdetector.fake_job_detector.Auth.RegisterRequestDto;
import com.fakejobdetector.fake_job_detector.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto request){
        String result=authService.register(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request){
        LoginResponseDto response=authService.login(request);
        return ResponseEntity.ok(response);
    }
}
