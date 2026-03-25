package com.learning.platform.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class AnalyticsResponse {
    private int totalAttempts;
    private double averageScorePercentage;
    private List<String> weakTopics;
    private List<String> strongTopics;
}
