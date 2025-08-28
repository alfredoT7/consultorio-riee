package com.fredodev.riee.appointment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {
    private Long id;
    private String fechaCita;
    private String horaCita;
    private String estadoCita;
    private Long patientId;
    private Long treatmentId;
}
