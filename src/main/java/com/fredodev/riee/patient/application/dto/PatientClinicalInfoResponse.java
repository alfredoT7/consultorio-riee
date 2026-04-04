package com.fredodev.riee.patient.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientClinicalInfoResponse {

    private Long id;
    private Long patientId;
    private String motivoConsulta;
    private String alergias;
    private String observaciones;
}
