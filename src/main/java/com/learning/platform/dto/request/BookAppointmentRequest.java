package com.learning.platform.dto.request;

import lombok.Data;

@Data
public class BookAppointmentRequest {
    private Long facultyId;
    private Long slotId;
}
