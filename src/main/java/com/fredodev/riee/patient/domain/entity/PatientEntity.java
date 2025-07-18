package com.fredodev.riee.patient.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "patients")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private long ciPaciente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "estado_civil_id", nullable = false)
    private CivilStatusEntity estadoCivil;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaNacimiento;

    @Column(nullable = false, length = 255)
    private String direccion;

    @Column(length = 100)
    private String ocupacion;

    @Column(length = 100)
    private String personaDeReferencia;

    @Column
    private long numeroPersonaRef;

    @Column(length = 255)
    private String imagen;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false)
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhoneNumberEntity> phonesNumbers;
}
