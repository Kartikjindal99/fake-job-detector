package com.fakejobdetector.fake_job_detector.Service;

import com.fakejobdetector.fake_job_detector.Auth.LoginRequestDto;
import com.fakejobdetector.fake_job_detector.Auth.LoginResponseDto;
import com.fakejobdetector.fake_job_detector.Auth.RegisterRequestDto;
import com.fakejobdetector.fake_job_detector.Config.jwtUtil;
import com.fakejobdetector.fake_job_detector.Entities.Role;
import com.fakejobdetector.fake_job_detector.Entities.User;
import com.fakejobdetector.fake_job_detector.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final jwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public String register(RegisterRequestDto request){
        if(userRepo.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already registerd");
        }
        User user=modelMapper.map(request,User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepo.save(user);
        return "Registerd Successfully!";
    }

    //login
    public LoginResponseDto login(LoginRequestDto request){
        User user=userRepo.findByEmail(request.getEmail())
                .orElseThrow(()->new RuntimeException("User not Found"));

        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new RuntimeException("Wrong Password!");
        }

        String token =jwtUtil.generateToken(user.getEmail(),user.getRole().name());

        LoginResponseDto response=modelMapper.map(user,LoginResponseDto.class);
        response.setToken(token);
        response.setRole(user.getRole().name());

        return response;
    }

}
