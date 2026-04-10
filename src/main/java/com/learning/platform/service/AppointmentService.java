package com.learning.platform.service;

import com.learning.platform.dto.request.BookAppointmentRequest;
import com.learning.platform.dto.request.RescheduleRequest;
import com.learning.platform.model.*;
import com.learning.platform.repository.AppointmentRepository;
import com.learning.platform.repository.AvailableSlotRepository;
import com.learning.platform.repository.FacultyRepository;
import com.learning.platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AvailableSlotRepository availableSlotRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private UserRepository userRepository;

    public Appointment bookAppointment(Long studentId, BookAppointmentRequest request) {
        User student = userRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Faculty faculty = facultyRepository.findById(request.getFacultyId()).orElseThrow(() -> new RuntimeException("Faculty not found"));
        AvailableSlot slot = availableSlotRepository.findById(request.getSlotId()).orElseThrow(() -> new RuntimeException("Slot not found"));

        if (!slot.getFaculty().getId().equals(faculty.getId())) {
            throw new RuntimeException("Slot does not belong to the selected faculty");
        }
        if (slot.isBooked()) {
            throw new RuntimeException("Slot is already booked");
        }

        Appointment appointment = new Appointment();
        appointment.setStudent(student);
        appointment.setFaculty(faculty);
        appointment.setSlot(slot);
        appointment.setStatus(EAppointmentStatus.PENDING);
        
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getStudentAppointments(Long studentId) {
        return appointmentRepository.findByStudentId(studentId);
    }

    public List<Appointment> getFacultyAppointments(Long facultyId) {
        return appointmentRepository.findByFacultyId(facultyId);
    }

    public Appointment approveAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new RuntimeException("Appointment not found"));
        if (appointment.getSlot().isBooked()) {
            throw new RuntimeException("Slot is already booked by someone else");
        }
        appointment.setStatus(EAppointmentStatus.APPROVED);
        appointment.getSlot().setBooked(true);
        availableSlotRepository.save(appointment.getSlot());
        return appointmentRepository.save(appointment);
    }

    public Appointment rejectAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus(EAppointmentStatus.REJECTED);
        return appointmentRepository.save(appointment);
    }

    public Appointment rescheduleAppointment(Long appointmentId, RescheduleRequest request) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new RuntimeException("Appointment not found"));
        
        if (appointment.getStatus() == EAppointmentStatus.APPROVED) {
            appointment.getSlot().setBooked(false);
            availableSlotRepository.save(appointment.getSlot());
        }

        AvailableSlot newSlot = new AvailableSlot();
        newSlot.setFaculty(appointment.getFaculty());
        newSlot.setDate(request.getDate());
        newSlot.setStartTime(request.getStartTime());
        newSlot.setEndTime(request.getEndTime());
        newSlot.setBooked(true); 
        newSlot = availableSlotRepository.save(newSlot);
        
        appointment.setSlot(newSlot);
        appointment.setStatus(EAppointmentStatus.RESCHEDULED);
        
        return appointmentRepository.save(appointment);
    }
}
