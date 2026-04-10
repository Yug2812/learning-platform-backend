package com.learning.platform.repository;

import com.learning.platform.model.AvailableSlot;
import com.learning.platform.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvailableSlotRepository extends JpaRepository<AvailableSlot, Long> {
    List<AvailableSlot> findByFacultyId(Long facultyId);
    List<AvailableSlot> findByFacultyIdAndDate(Long facultyId, LocalDate date);
    List<AvailableSlot> findByFacultyAndIsBookedFalse(Faculty faculty);
}
