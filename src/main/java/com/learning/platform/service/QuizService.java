package com.learning.platform.service;

import com.learning.platform.dto.request.QuestionRequest;
import com.learning.platform.dto.request.QuizSubmitRequest;
import com.learning.platform.dto.response.QuizResultResponse;
import com.learning.platform.model.*;
import com.learning.platform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private QuizAttemptRepository quizAttemptRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActivityLogRepository activityLogRepository;

    public Question createQuestion(QuestionRequest request) {
        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new RuntimeException("Topic not found"));
        Question q = new Question();
        q.setText(request.getText());
        q.setOptionA(request.getOptionA());
        q.setOptionB(request.getOptionB());
        q.setOptionC(request.getOptionC());
        q.setOptionD(request.getOptionD());
        q.setCorrectOption(request.getCorrectOption());
        q.setDifficulty(request.getDifficulty());
        q.setTopic(topic);
        return questionRepository.save(q);
    }

    public List<Question> getQuestionsByTopic(Long topicId) {
        List<Question> questions = questionRepository.findByTopicId(topicId);
        Collections.shuffle(questions);
        return questions.stream().limit(10).collect(Collectors.toList());
    }

    public QuizResultResponse submitQuiz(Long userId, QuizSubmitRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        Topic topic = topicRepository.findById(request.getTopicId()).orElseThrow();
        
        List<Question> questions = questionRepository.findByTopicId(topic.getId());
        Map<Long, String> correctAnswers = questions.stream()
                .collect(Collectors.toMap(Question::getId, Question::getCorrectOption));

        int score = 0;
        for (Map.Entry<Long, String> entry : request.getAnswers().entrySet()) {
            if (entry.getValue().equalsIgnoreCase(correctAnswers.get(entry.getKey()))) {
                score++;
            }
        }

        int totalQuestions = request.getAnswers().size();
        if (totalQuestions == 0) totalQuestions = 1; // prevent div bypass

        QuizAttempt attempt = new QuizAttempt();
        attempt.setUser(user);
        attempt.setTopic(topic);
        attempt.setScore(score);
        attempt.setTotalQuestions(totalQuestions);
        attempt.setStartTime(request.getStartTime());
        attempt.setEndTime(request.getEndTime());
        quizAttemptRepository.save(attempt);

        // Cybersecurity: Suspicious behavior checking
        long durationSeconds = 0;
        if (request.getStartTime() != null && request.getEndTime() != null) {
            durationSeconds = Duration.between(request.getStartTime(), request.getEndTime()).getSeconds();
        }
        
        boolean suspicious = false;
        if (durationSeconds < totalQuestions * 2L) { // Extremely fast completion
            suspicious = true;
        }

        ActivityLog log = new ActivityLog();
        log.setUser(user);
        log.setActivityType("QUIZ_ATTEMPT");
        log.setDetails("Attempted quiz on topic: " + topic.getTitle() + " Score: " + score + ". Suspicious: " + suspicious);
        activityLogRepository.save(log);

        QuizResultResponse res = new QuizResultResponse();
        res.setQuizAttemptId(attempt.getId());
        res.setScore(score);
        res.setTotalQuestions(attempt.getTotalQuestions());
        res.setPercentage((double) score / attempt.getTotalQuestions() * 100);
        res.setFlaggedSuspicious(suspicious);
        res.setMessage(suspicious ? "Quiz completed abnormally fast. Flagged for review." : "Quiz submitted successfully.");
        return res;
    }
}
