package com.fredodev.riee.treatment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DentistTreatmentHistoryResponse {
    private Long id;
    private Long treatmentHistoryId;
    private Long dentistId;
    private String dentistNombre;
    private String dentistEmail;
    private Boolean dentistaPrincipal;
    private String notas;
    private LocalDateTime fechaAsignacion;
}
