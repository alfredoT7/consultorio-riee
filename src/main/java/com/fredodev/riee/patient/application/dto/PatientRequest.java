package com.fredodev.riee.patient.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequest {
    @Positive(message = "El CI del paciente debe ser un numero positivo")
    private long ciPaciente;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato valido")
    @Size(max = 255, message = "El email debe tener maximo 255 caracteres")
    private String email;

    @NotBlank(message = "El estado civil es obligatorio")
    private String estadoCivil;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "La direccion es obligatoria")
    @Size(max = 255, message = "La direccion debe tener maximo 255 caracteres")
    private String direccion;

    @Size(max = 100, message = "La ocupacion debe tener maximo 100 caracteres")
    private String ocupacion;

    @Size(max = 100, message = "La persona de referencia debe tener maximo 100 caracteres")
    private String personaDeReferencia;

    @Positive(message = "El numero de la persona de referencia debe ser positivo")
    private Long numeroPersonaRef;

    private MultipartFile imagen;

    @NotBlank(message = "El nombre del paciente es obligatorio")
    @Size(max = 100, message = "El nombre debe tener maximo 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido del paciente es obligatorio")
    @Size(max = 100, message = "El apellido debe tener maximo 100 caracteres")
    private String apellido;

    @NotEmpty(message = "Debe registrar al menos un telefono")
    private List<@Valid PhoneNumberRequest> phonesNumbers;
}
