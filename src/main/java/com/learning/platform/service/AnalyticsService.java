package com.learning.platform.service;

import com.learning.platform.dto.response.AnalyticsResponse;
import com.learning.platform.model.QuizAttempt;
import com.learning.platform.repository.QuizAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    public AnalyticsResponse getUserAnalytics(Long userId) {
        List<QuizAttempt> attempts = quizAttemptRepository.findByUserId(userId);
        
        AnalyticsResponse res = new AnalyticsResponse();
        if (attempts.isEmpty()) {
            res.setTotalAttempts(0);
            res.setAverageScorePercentage(0.0);
            res.setWeakTopics(new ArrayList<>());
            res.setStrongTopics(new ArrayList<>());
            return res;
        }

        res.setTotalAttempts(attempts.size());
        
        double avgScore = attempts.stream()
                .mapToDouble(a -> (double) a.getScore() / a.getTotalQuestions() * 100)
                .average()
                .orElse(0.0);
        res.setAverageScorePercentage(avgScore);

        Map<String, Double> topicAverages = attempts.stream()
                .collect(Collectors.groupingBy(
                        a -> a.getTopic().getTitle(),
                        Collectors.averagingDouble(a -> (double) a.getScore() / a.getTotalQuestions() * 100)
                ));

        List<String> weakTopics = new ArrayList<>();
        List<String> strongTopics = new ArrayList<>();

        for (Map.Entry<String, Double> entry : topicAverages.entrySet()) {
            if (entry.getValue() < 50.0) {
                weakTopics.add(entry.getKey());
            } else {
                strongTopics.add(entry.getKey());
            }
        }

        res.setWeakTopics(weakTopics);
        res.setStrongTopics(strongTopics);

        return res;
    }
}
