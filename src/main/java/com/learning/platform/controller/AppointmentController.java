package com.learning.platform.controller;

import com.learning.platform.dto.ApiResponse;
import com.learning.platform.dto.request.BookAppointmentRequest;
import com.learning.platform.dto.request.RescheduleRequest;
import com.learning.platform.model.Appointment;
import com.learning.platform.security.services.UserDetailsImpl;
import com.learning.platform.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<ApiResponse<Appointment>> bookAppointment(@RequestBody BookAppointmentRequest request, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Appointment appointment = appointmentService.bookAppointment(userDetails.getId(), request);
        return ResponseEntity.ok(new ApiResponse<>("success", "Appointment booked successfully", appointment));
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<ApiResponse<List<Appointment>>> getStudentAppointments(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>("success", "Fetched student appointments", appointmentService.getStudentAppointments(id)));
    }

    @GetMapping("/faculty/{id}")
    public ResponseEntity<ApiResponse<List<Appointment>>> getFacultyAppointments(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>("success", "Fetched faculty appointments", appointmentService.getFacultyAppointments(id)));
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<ApiResponse<Appointment>> approveAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>("success", "Appointment approved", appointmentService.approveAppointment(id)));
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<ApiResponse<Appointment>> rejectAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>("success", "Appointment rejected", appointmentService.rejectAppointment(id)));
    }

    @PutMapping("/reschedule/{id}")
    public ResponseEntity<ApiResponse<Appointment>> rescheduleAppointment(@PathVariable Long id, @RequestBody RescheduleRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("success", "Appointment rescheduled", appointmentService.rescheduleAppointment(id, request)));
    }
}
