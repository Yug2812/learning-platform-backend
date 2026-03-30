package com.learning.platform.controller;

import com.learning.platform.dto.request.QuestionRequest;
import com.learning.platform.dto.request.QuizSubmitRequest;
import com.learning.platform.dto.response.QuizResultResponse;
import com.learning.platform.model.Question;
import com.learning.platform.security.services.UserDetailsImpl;
import com.learning.platform.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/admin/questions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody QuestionRequest request) {
        return ResponseEntity.ok(quizService.createQuestion(request));
    }

    @GetMapping("/student/topics/{topicId}/questions")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<List<Question>> getQuestionsByTopic(@PathVariable Long topicId) {
        return ResponseEntity.ok(quizService.getQuestionsByTopic(topicId));
    }

    @PostMapping("/student/quiz/submit")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<QuizResultResponse> submitQuiz(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody QuizSubmitRequest request) {
        return ResponseEntity.ok(quizService.submitQuiz(userDetails.getId(), request));
    }
}
