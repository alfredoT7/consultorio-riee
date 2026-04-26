package com.fredodev.riee.appointment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentCalendarResponse {
    private Long id;
    private Date fechaCita;
    private Time horaCita;
    private Long duracionEstimada;
    private Long patientId;
    private String patientNombreCompleto;
    private Long appointmentStatusId;
    private String appointmentStatusName;
}

