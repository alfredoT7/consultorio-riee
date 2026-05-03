package com.fredodev.riee.patient.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponse {
    private Long id;
    private long ciPaciente;
    private String email;
    private CivilStatusResponse estadoCivil;
    private Date fechaNacimiento;
    private String direccion;
    private String ocupacion;
    private String personaDeReferencia;
    private long numeroPersonaRef;
    private String imagen;
    private String imagenPublicId;
    private String nombre;
    private String apellido;
    private List<PhoneNumberResponse> phonesNumbers;
    private PatientNextAppointmentResponse proximaCita;
}
