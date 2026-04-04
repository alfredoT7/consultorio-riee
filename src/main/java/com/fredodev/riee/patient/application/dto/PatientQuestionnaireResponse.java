package com.fredodev.riee.patient.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientQuestionnaireResponse {

    private Long id;
    private Long patientId;
    private boolean estaBajoTratamientoMedicoActualmente;
    private boolean tomaMedicamentosRegularmente;
    private boolean haTenidoCirugiasImportantes;
    private boolean esHipertenso;
    private boolean esDiabetico;
    private boolean tieneProblemasCardiacos;
    private boolean tieneProblemasCoagulacionOSangraFacilmente;
    private boolean esAlergicoAMedicamentosOAnestesia;
    private boolean haTenidoHepatitisOEnfermedadInfecciosaImportante;
    private boolean padeceAsmaOProblemasRespiratorios;
    private boolean fuma;
    private boolean consumeAlcoholFrecuentemente;
    private boolean leSangranLasEncias;
    private boolean tieneDolorOSensibilidadDental;
    private boolean haTenidoProblemasConTratamientosDentalesAnteriores;
    private boolean estaEmbarazadaOLactancia;
}
