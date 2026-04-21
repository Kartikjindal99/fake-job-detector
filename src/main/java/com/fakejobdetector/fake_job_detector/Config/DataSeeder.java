package com.fakejobdetector.fake_job_detector.Config;
import com.fakejobdetector.fake_job_detector.Entities.Role;
import com.fakejobdetector.fake_job_detector.Entities.User;
import com.fakejobdetector.fake_job_detector.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // existsByEmail se check karo
        boolean adminExists = userRepo
                .existsByEmail("admin@fakejob.com");

        System.out.println("Admin exists: " + adminExists);

        if (!adminExists) {
            User admin = User.builder()
                    .name("Admin")
                    .email("admin@fakejob.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();

            userRepo.save(admin);
            System.out.println("✅ Admin created!");
        } else {
            System.out.println("✅ Admin already exists — skipping!");
        }
    }
}