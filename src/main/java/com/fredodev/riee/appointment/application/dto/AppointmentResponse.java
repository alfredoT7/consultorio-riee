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
public class AppointmentResponse {
    private Long id;
    private Date fechaCita;
    private Time horaCita;
    private String motivoCita;
    private String observacionesCita;
    private Long duracionEstimada;
    private AppointmentPatientSummaryResponse patient;
    private AppointmentStatusSummaryResponse status;
}
