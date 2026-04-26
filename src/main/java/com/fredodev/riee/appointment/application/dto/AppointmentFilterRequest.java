package com.fredodev.riee.appointment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentFilterRequest {
    private Date fromDate;
    private Date toDate;
    private Integer patientCi;
    private Long patientId;
    private Long appointmentStatusId;
}

