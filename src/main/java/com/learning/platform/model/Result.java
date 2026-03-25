package com.learning.platform.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "results")
@Data
@NoArgsConstructor
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_attempt_id")
    private QuizAttempt quizAttempt;

    private String feedback;
}
