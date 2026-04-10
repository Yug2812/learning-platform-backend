package com.learning.platform.repository;

import com.learning.platform.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByStudentId(Long studentId);
    List<Appointment> findByFacultyId(Long facultyId);
}
