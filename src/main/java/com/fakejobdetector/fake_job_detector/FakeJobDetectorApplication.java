package com.fakejobdetector.fake_job_detector;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FakeJobDetectorApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

	public static void main(String[] args) {
		SpringApplication.run(FakeJobDetectorApplication.class, args);
	}


}
