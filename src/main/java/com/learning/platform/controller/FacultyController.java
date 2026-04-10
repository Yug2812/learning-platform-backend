package com.learning.platform.controller;

import com.learning.platform.dto.ApiResponse;
import com.learning.platform.dto.request.SlotRequest;
import com.learning.platform.model.AvailableSlot;
import com.learning.platform.model.Faculty;
import com.learning.platform.service.FacultyService;
import com.learning.platform.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/faculty")
public class FacultyController {
    @Autowired
    private FacultyService facultyService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Faculty>>> getAllFaculty() {
        return ResponseEntity.ok(new ApiResponse<>("success", "Faculty list fetched", facultyService.getAllFaculty()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Faculty>> getFacultyById(@PathVariable Long id) {
        Faculty faculty = facultyService.getFacultyById(id);
        if (faculty == null) return ResponseEntity.badRequest().body(new ApiResponse<>("error", "Not found", null));
        return ResponseEntity.ok(new ApiResponse<>("success", "Faculty fetched", faculty));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Faculty>> getMyFaculty(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Faculty faculty = facultyService.getFacultyByUserId(userDetails.getId());
        if (faculty == null) return ResponseEntity.badRequest().body(new ApiResponse<>("error", "Not found", null));
        return ResponseEntity.ok(new ApiResponse<>("success", "Faculty profile fetched", faculty));
    }

    @PostMapping("/{id}/slots")
    public ResponseEntity<ApiResponse<AvailableSlot>> addSlot(@PathVariable Long id, @RequestBody SlotRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("success", "Slot added", facultyService.addSlot(id, request)));
    }

    @GetMapping("/{id}/slots")
    public ResponseEntity<ApiResponse<List<AvailableSlot>>> getSlots(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>("success", "Slots fetched", facultyService.getSlots(id)));
    }

    @GetMapping("/{id}/available-slots")
    public ResponseEntity<ApiResponse<List<AvailableSlot>>> getAvailableSlots(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>("success", "Available slots fetched", facultyService.getAvailableSlots(id)));
    }
}
