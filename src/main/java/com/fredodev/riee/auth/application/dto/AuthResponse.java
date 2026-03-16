package com.fredodev.riee.auth.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private Long id;
    private String nombres;
    private String apellidos;
    private String token;
    @Default
    private String type = "Bearer";
    private String imagenUrl;
}
