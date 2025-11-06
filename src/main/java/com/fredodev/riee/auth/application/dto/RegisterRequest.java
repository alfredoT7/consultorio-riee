package com.fredodev.riee.auth.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "El nombre es requerido")
    private String nombres;

    @NotBlank(message = "El apellido es requerido")
    private String apellidos;

    @NotBlank(message = "El email es requerido")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "El nombre de usuario es requerido")
    private String username;

    private Long telefono;

    private Long ciDentista;

    private String universidad;

    private Long promocion;

    private String imagenUrl;

    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    private List<Long> especialidadIds;
}
