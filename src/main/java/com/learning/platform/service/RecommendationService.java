package com.learning.platform.service;

import com.learning.platform.model.QuizAttempt;
import com.learning.platform.model.Recommendation;
import com.learning.platform.model.User;
import com.learning.platform.repository.QuizAttemptRepository;
import com.learning.platform.repository.RecommendationRepository;
import com.learning.platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class RecommendationService {

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private UserRepository userRepository;

    @SuppressWarnings("rawtypes")
    public List<Recommendation> generateRecommendations(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<QuizAttempt> attempts = quizAttemptRepository.findByUserIdOrderByAttemptedAtDesc(userId);

        if (attempts.isEmpty()) {
            return Collections.emptyList();
        }

        QuizAttempt latest = attempts.get(0);
        double scorePercent = (double) latest.getScore() / latest.getTotalQuestions() * 100;
        
        long durationSeconds = 120; // default fallback
        if (latest.getStartTime() != null && latest.getEndTime() != null) {
            durationSeconds = Duration.between(latest.getStartTime(), latest.getEndTime()).getSeconds();
        }

        Recommendation rec = new Recommendation();
        rec.setUser(user);
        rec.setRecommendedTopic(latest.getTopic());

        // Ask Python ML Service
        String mlLevel = "MEDIUM"; // default
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8000/predict";
            
            Map<String, Object> requestBody = Map.of(
                "score", scorePercent,
                "time_taken", durationSeconds
            );

            ResponseEntity<Map> response = restTemplate.postForEntity(url, requestBody, Map.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                mlLevel = (String) response.getBody().get("level");
            }
        } catch (Exception e) {
            System.err.println("Warning: ML Service unreachable. Falling back to default.");
        }

        if ("WEAK".equalsIgnoreCase(mlLevel)) {
            rec.setRecommendationType("EASY");
            rec.setReason("AI Analysis (WEAK): Your score and time taken indicates you should start with EASY foundational topics.");
        } else if ("STRONG".equalsIgnoreCase(mlLevel)) {
            rec.setRecommendationType("HARD");
            rec.setReason("AI Analysis (STRONG): Excellent performance! We recommend challenging yourself with HARD topics.");
        } else {
            rec.setRecommendationType("MEDIUM");
            rec.setReason("AI Analysis (MEDIUM): You have a decent grasp. We recommend practicing MEDIUM topics to improve.");
        }

        recommendationRepository.save(rec);

        return recommendationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
