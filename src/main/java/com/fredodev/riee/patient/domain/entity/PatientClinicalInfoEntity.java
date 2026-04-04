package com.fredodev.riee.patient.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "patient_clinical_info")
@Getter
@Setter
@NoArgsConstructor
public class PatientClinicalInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false, unique = true)
    private PatientEntity patient;

    @Column(nullable = false, length = 500)
    private String motivoConsulta;

    @Column(nullable = false, length = 1000)
    private String alergias;

    @Column(nullable = false, length = 2000)
    private String observaciones;
}
