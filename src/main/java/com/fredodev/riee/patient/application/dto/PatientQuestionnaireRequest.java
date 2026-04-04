package com.fredodev.riee.patient.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientQuestionnaireRequest {

    @NotNull(message = "Debe indicar si esta bajo tratamiento medico actualmente")
    private Boolean estaBajoTratamientoMedicoActualmente;

    @NotNull(message = "Debe indicar si toma medicamentos regularmente")
    private Boolean tomaMedicamentosRegularmente;

    @NotNull(message = "Debe indicar si ha tenido cirugias importantes")
    private Boolean haTenidoCirugiasImportantes;

    @NotNull(message = "Debe indicar si es hipertenso")
    private Boolean esHipertenso;

    @NotNull(message = "Debe indicar si es diabetico")
    private Boolean esDiabetico;

    @NotNull(message = "Debe indicar si tiene problemas cardiacos")
    private Boolean tieneProblemasCardiacos;

    @NotNull(message = "Debe indicar si tiene problemas de coagulacion o sangra facilmente")
    private Boolean tieneProblemasCoagulacionOSangraFacilmente;

    @NotNull(message = "Debe indicar si es alergico a medicamentos o anestesia")
    private Boolean esAlergicoAMedicamentosOAnestesia;

    @NotNull(message = "Debe indicar si ha tenido hepatitis o enfermedad infecciosa importante")
    private Boolean haTenidoHepatitisOEnfermedadInfecciosaImportante;

    @NotNull(message = "Debe indicar si padece asma o problemas respiratorios")
    private Boolean padeceAsmaOProblemasRespiratorios;

    @NotNull(message = "Debe indicar si fuma")
    private Boolean fuma;

    @NotNull(message = "Debe indicar si consume alcohol frecuentemente")
    private Boolean consumeAlcoholFrecuentemente;

    @NotNull(message = "Debe indicar si le sangran las encias")
    private Boolean leSangranLasEncias;

    @NotNull(message = "Debe indicar si tiene dolor o sensibilidad dental")
    private Boolean tieneDolorOSensibilidadDental;

    @NotNull(message = "Debe indicar si ha tenido problemas con tratamientos dentales anteriores")
    private Boolean haTenidoProblemasConTratamientosDentalesAnteriores;

    @NotNull(message = "Debe indicar si esta embarazada o en lactancia")
    private Boolean estaEmbarazadaOLactancia;
}
