package com.fredodev.riee.patient.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientClinicalInfoRequest {

    @NotBlank(message = "El motivo de consulta es obligatorio")
    @Size(max = 500, message = "El motivo de consulta debe tener maximo 500 caracteres")
    private String motivoConsulta;

    @NotBlank(message = "Las alergias son obligatorias")
    @Size(max = 1000, message = "Las alergias deben tener maximo 1000 caracteres")
    private String alergias;

    @NotBlank(message = "Las observaciones son obligatorias")
    @Size(max = 2000, message = "Las observaciones deben tener maximo 2000 caracteres")
    private String observaciones;
}
