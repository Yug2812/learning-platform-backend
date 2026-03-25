package com.learning.platform.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "activity_logs")
@Data
@NoArgsConstructor
public class ActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String activityType; // e.g. "LOGIN_SUCCESS", "LOGIN_FAILED", "QUIZ_ATTEMPT"
    
    @Column(columnDefinition="TEXT")
    private String details;
    
    private String ipAddress;

    private LocalDateTime timestamp = LocalDateTime.now();
}
