package com.learning.platform.dto.response;

import lombok.Data;

@Data
public class QuizResultResponse {
    private Long quizAttemptId;
    private int score;
    private int totalQuestions;
    private double percentage;
    private boolean flaggedSuspicious;
    private String message;
}
