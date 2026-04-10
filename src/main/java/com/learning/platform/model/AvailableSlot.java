package com.learning.platform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "available_slots")
@Data
@NoArgsConstructor
public class AvailableSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    @JsonIgnore
    private Faculty faculty;

    private LocalDate date;
    
    private LocalTime startTime;
    
    private LocalTime endTime;

    private boolean isBooked = false;
}
