package com.learning.platform.dto.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class QuizSubmitRequest {
    private Long topicId;
    
    // Maps Question ID to selected Option String (e.g., "A", "B")
    private Map<Long, String> answers;
    
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
