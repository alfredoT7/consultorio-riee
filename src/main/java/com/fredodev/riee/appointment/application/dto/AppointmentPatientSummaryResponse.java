package com.fredodev.riee.appointment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentPatientSummaryResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private long ciPaciente;
    private String email;
    private String direccion;
}
