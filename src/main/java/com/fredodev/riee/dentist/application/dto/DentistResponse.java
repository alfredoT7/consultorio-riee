package com.fredodev.riee.dentist.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DentistResponse {
    private Long id;
    private String nombres;
    private String apellidos;
    private String email;
    private String username;
    private String telefono;
    private String ciDentista;
    private String universidad;
    private String promocion;
    private String imagenUrl;
    //mandar las especialidades como una lista de strings
    private List<String> especialidades;
}
