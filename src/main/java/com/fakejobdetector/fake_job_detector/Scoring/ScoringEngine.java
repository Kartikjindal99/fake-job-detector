package com.fakejobdetector.fake_job_detector.Scoring;
import com.fakejobdetector.fake_job_detector.Repository.BlacklistedCompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScoringEngine {

    private final BlacklistedCompanyRepo blacklistedCompanyRepo;

    public ScoringResult analyze(String jobTitle,
                                 String companyName,
                                 String description,
                                 String salary,
                                 String contactEmail) {

        int score = 0;
        List<String> flags = new ArrayList<>();

        // Rule 1 — Unrealistic salary claims
        if (description != null && containsAny(description,
                "earn lakhs", "earn daily", "no experience needed",
                "work from home earn", "part time earn",
                "ghar baithe kamao", "daily payment")) {
            score += 20;
            flags.add("Unrealistic earning claims detected");
        }

        // Rule 2 — Urgency language
        if (description != null && containsAny(description,
                "apply immediately", "urgent hiring",
                "only today", "limited seats", "hurry up",
                "last date today", "urgent requirement")) {
            score += 15;
            flags.add("Urgency language detected");
        }

        // Rule 3 — Gmail/Yahoo contact
        if (contactEmail != null && containsAny(contactEmail,
                "@gmail.com", "@yahoo.com", "@hotmail.com",
                "@rediffmail.com", "@outlook.com")) {
            score += 15;
            flags.add("Personal email used instead of company domain");
        }

        // Rule 4 — Asks for documents or money ✅ NEW
        if (description != null && containsAny(description,
                "aadhaar", "aadhar", "pan card", "bank details",
                "registration fee", "security deposit",
                "send documents", "pay fee",
                "payment required", "deposit required")) {
            score += 20;
            flags.add("Asks for personal documents or money upfront");
        }

        // Rule 5 — Vague description
        if (description != null) {
            int wordCount = description.trim()
                    .split("\\s+").length;
            if (wordCount < 20) {
                score += 5;
                flags.add("Very vague job description");
            }
        }

        // Rule 6 — No company name
        if (companyName == null || companyName.trim().isEmpty()) {
            score += 10;
            flags.add("No company name provided");
        }

        // Rule 7 — Excessive caps
        if (description != null) {
            long upperCount = description.chars()
                    .filter(Character::isUpperCase).count();
            long letterCount = description.chars()
                    .filter(Character::isLetter).count();
            if (letterCount > 0 &&
                    (double) upperCount / letterCount > 0.1) {
                score += 10;
                flags.add("Excessive use of capital letters");
            }
        }

        // Rule 8 — Company blacklisted
        if (companyName != null && !companyName.trim().isEmpty()) {
            boolean isBlacklisted = blacklistedCompanyRepo
                    .existsByCompanyNameIgnoreCase(companyName.trim());
            if (isBlacklisted) {
                score += 20;
                flags.add("Company is blacklisted in our database");
            }
        }

        score = Math.min(score, 100);
        String verdict = getVerdict(score);

        return ScoringResult.builder()
                .score(score)
                .verdict(verdict)
                .flags(flags)
                .build();
    }

    private String getVerdict(int score) {
        if (score <= 30) return "LEGIT";
        if (score <= 60) return "SUSPICIOUS";
        return "FAKE";
    }

    private boolean containsAny(String text, String... keywords) {
        String lowerText = text.toLowerCase();
        for (String keyword : keywords) {
            if (lowerText.contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}