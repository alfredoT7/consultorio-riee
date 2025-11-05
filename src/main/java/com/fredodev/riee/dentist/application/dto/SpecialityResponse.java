package com.fredodev.riee.dentist.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialityResponse {
    private Long id;
    private String nombre;
    private String descripcion;
}

