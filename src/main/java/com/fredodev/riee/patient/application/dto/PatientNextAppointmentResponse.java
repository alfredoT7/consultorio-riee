package com.fredodev.riee.patient.application.dto;

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
public class PatientNextAppointmentResponse {
    private Long id;
    private Date fechaCita;
    private Time horaCita;
    private String motivoCita;
    private String estadoCita;
    private Long duracionEstimada;
    private boolean tieneCita;
    private String mensaje;
}
