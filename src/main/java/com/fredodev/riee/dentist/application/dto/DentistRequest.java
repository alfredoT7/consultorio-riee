package com.fredodev.riee.dentist.application.dto;

import com.fredodev.riee.annotation.boliviaphone.BoliviaPhone;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DentistRequest {
    @NotBlank(message = "El nombre del Dentista es obligatorio")
    @Size(max = 255, message = "El nombre del Dentista debe tener máximo 255 caracteres")
    private String nombres;
    @NotBlank(message = "El apellido del Dentista es obligatorio")
    @Size(max = 255, message = "El apellido del Dentista debe tener máximo 255 caracteres")
    private String apellidos;
    @NotBlank(message = "El email es obligatorio")
    @Size(max = 255, message = "El email debe tener máximo 255 caracteres")
    private String email;
    @NotBlank(message = "El username es obligatorio")
    @Size(max = 35, message = "El username debe tener máximo 35 caracteres")
    private String username;
    @NotNull(message = "El teléfono es obligatorio")
    @BoliviaPhone
    private Long telefono;
    @NotNull(message = "La cédula es obligatoria")
    @Positive(message = "La cédula debe ser un número positivo")
    private Long ciDentista;

    @Size(max = 255, message = "La universidad debe tener máximo 255 caracteres")
    private String universidad;

    @Positive(message = "La promoción debe ser un número positivo")
    private Long promocion;

    @Size(max = 1000, message = "La URL de la imagen es demasiado larga")
    @Pattern(
            regexp = "^(https?:\\/\\/).+$",
            message = "La imagen debe ser una URL válida que empiece con http:// o https://"
    )
    private String imagenUrl;

    // IDs de especialidades
    private List<Long> especialidadIds;
}
