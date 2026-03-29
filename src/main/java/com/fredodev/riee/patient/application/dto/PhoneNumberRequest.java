package com.fredodev.riee.patient.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberRequest {
    @NotBlank(message = "El numero de telefono es obligatorio")
    @Size(max = 20, message = "El numero de telefono debe tener maximo 20 caracteres")
    private String numero;
}
