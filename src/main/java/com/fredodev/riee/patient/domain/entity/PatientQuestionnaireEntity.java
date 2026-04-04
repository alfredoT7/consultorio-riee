package com.fredodev.riee.patient.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "patient_questionnaires")
@Getter
@Setter
@NoArgsConstructor
public class PatientQuestionnaireEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false, unique = true)
    private PatientEntity patient;

    @Column(nullable = false)
    private boolean estaBajoTratamientoMedicoActualmente;

    @Column(nullable = false)
    private boolean tomaMedicamentosRegularmente;

    @Column(nullable = false)
    private boolean haTenidoCirugiasImportantes;

    @Column(nullable = false)
    private boolean esHipertenso;

    @Column(nullable = false)
    private boolean esDiabetico;

    @Column(nullable = false)
    private boolean tieneProblemasCardiacos;

    @Column(nullable = false)
    private boolean tieneProblemasCoagulacionOSangraFacilmente;

    @Column(nullable = false)
    private boolean esAlergicoAMedicamentosOAnestesia;

    @Column(nullable = false)
    private boolean haTenidoHepatitisOEnfermedadInfecciosaImportante;

    @Column(nullable = false)
    private boolean padeceAsmaOProblemasRespiratorios;

    @Column(nullable = false)
    private boolean fuma;

    @Column(nullable = false)
    private boolean consumeAlcoholFrecuentemente;

    @Column(nullable = false)
    private boolean leSangranLasEncias;

    @Column(nullable = false)
    private boolean tieneDolorOSensibilidadDental;

    @Column(nullable = false)
    private boolean haTenidoProblemasConTratamientosDentalesAnteriores;

    @Column(nullable = false)
    private boolean estaEmbarazadaOLactancia;
}
