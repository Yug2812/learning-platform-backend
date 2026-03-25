package com.learning.platform.service;

import com.learning.platform.model.QuizAttempt;
import com.learning.platform.model.Recommendation;
import com.learning.platform.model.User;
import com.learning.platform.repository.QuizAttemptRepository;
import com.learning.platform.repository.RecommendationRepository;
import com.learning.platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RecommendationService {

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Recommendation> generateRecommendations(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<QuizAttempt> attempts = quizAttemptRepository.findByUserIdOrderByAttemptedAtDesc(userId);

        if (attempts.isEmpty()) {
            return Collections.emptyList();
        }

        QuizAttempt latest = attempts.get(0);
        double scorePercent = (double) latest.getScore() / latest.getTotalQuestions() * 100;

        Recommendation rec = new Recommendation();
        rec.setUser(user);
        rec.setRecommendedTopic(latest.getTopic());

        if (scorePercent < 40) {
            rec.setRecommendationType("EASY");
            rec.setReason("Your recent score was below 40%. We recommend starting with EASY topics and fundamental concepts.");
        } else if (scorePercent <= 70) {
            rec.setRecommendationType("MEDIUM");
            rec.setReason("You have a decent grasp of the material (40-70%). We recommend practicing MEDIUM topics to improve.");
        } else {
            rec.setRecommendationType("HARD");
            rec.setReason("Great job scoring above 70%! We recommend challenging yourself with HARD topics.");
        }

        recommendationRepository.save(rec);

        return recommendationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
