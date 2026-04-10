package com.learning.platform.service;

import com.learning.platform.dto.request.SlotRequest;
import com.learning.platform.model.AvailableSlot;
import com.learning.platform.model.Faculty;
import com.learning.platform.model.User;
import com.learning.platform.repository.AvailableSlotRepository;
import com.learning.platform.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private AvailableSlotRepository availableSlotRepository;

    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }
    
    public Faculty getFacultyByUserId(Long userId) {
        User user = new User();
        user.setId(userId);
        return facultyRepository.findByUser(user).orElse(null);
    }

    public AvailableSlot addSlot(Long facultyId, SlotRequest request) {
        Faculty faculty = facultyRepository.findById(facultyId)
            .orElseThrow(() -> new RuntimeException("Faculty not found"));
        AvailableSlot slot = new AvailableSlot();
        slot.setFaculty(faculty);
        slot.setDate(request.getDate());
        slot.setStartTime(request.getStartTime());
        slot.setEndTime(request.getEndTime());
        slot.setBooked(false);
        return availableSlotRepository.save(slot);
    }

    public List<AvailableSlot> getSlots(Long facultyId) {
        return availableSlotRepository.findByFacultyId(facultyId);
    }
    
    public List<AvailableSlot> getAvailableSlots(Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId)
            .orElseThrow(() -> new RuntimeException("Faculty not found"));
        return availableSlotRepository.findByFacultyAndIsBookedFalse(faculty);
    }
}
