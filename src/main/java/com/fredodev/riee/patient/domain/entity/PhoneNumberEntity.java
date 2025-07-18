package com.fredodev.riee.patient.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "phone_number")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PhoneNumberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String numero;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private PatientEntity patient;
}
