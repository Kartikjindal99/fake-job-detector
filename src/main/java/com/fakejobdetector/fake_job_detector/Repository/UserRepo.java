package com.fakejobdetector.fake_job_detector.Repository;

import com.fakejobdetector.fake_job_detector.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User>findByEmail(String Email);
    boolean existsByEmail(String Email);
}
