package com.learning.platform.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
@Data
@NoArgsConstructor
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommended_topic_id")
    private Topic recommendedTopic;

    @Column(columnDefinition="TEXT")
    private String reason;

    private LocalDateTime createdAt = LocalDateTime.now();
}
