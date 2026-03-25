package com.learning.platform.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition="TEXT")
    private String text;

    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctOption;

    private String difficulty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;
}
