package com.learning.platform.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Map;
import java.util.List;

@Service
public class ChatService {

    @Value("${huggingface.api.key:your_hf_api_key_here}")
    private String hfApiKey;

    @Value("${huggingface.api.url:https://api-inference.huggingface.co/models/facebook/blenderbot-400M-distill}")
    private String hfApiUrl;

    @SuppressWarnings({"rawtypes", "unchecked"})
    public String generateResponse(String userMessage) {
        if ("your_hf_api_key_here".equals(hfApiKey)) {
            return "I am connected to the backend, but the HuggingFace API key is missing from `application.yml`. Please configure it so I can talk to the real AI model!";
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(hfApiKey);

            Map<String, Object> requestBody = Map.of("inputs", userMessage);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<List> response = restTemplate.postForEntity(hfApiUrl, entity, List.class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && !response.getBody().isEmpty()) {
                Map<String, Object> firstResult = (Map<String, Object>) response.getBody().get(0);
                if (firstResult.containsKey("generated_text")) {
                    return (String) firstResult.get("generated_text");
                }
            }
            return "I'm sorry, I couldn't generate a response at this time.";
        } catch (Exception e) {
            return "Error communicating with AI Chatbot: " + e.getMessage();
        }
    }
}
