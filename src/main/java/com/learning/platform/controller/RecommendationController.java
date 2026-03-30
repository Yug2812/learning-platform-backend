package com.learning.platform.controller;

import com.learning.platform.model.Recommendation;
import com.learning.platform.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ai")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/recommendations/{userId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<List<Recommendation>> getRecommendations(@PathVariable Long userId) {
        return ResponseEntity.ok(recommendationService.generateRecommendations(userId));
    }
}
