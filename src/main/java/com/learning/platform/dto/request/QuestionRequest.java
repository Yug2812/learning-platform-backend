package com.learning.platform.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuestionRequest {
    @NotBlank
    private String text;
    @NotBlank
    private String optionA;
    @NotBlank
    private String optionB;
    @NotBlank
    private String optionC;
    @NotBlank
    private String optionD;
    @NotBlank
    private String correctOption;
    @NotBlank
    private String difficulty; // EASY, MEDIUM, HARD
    @NotNull
    private Long topicId;
}
