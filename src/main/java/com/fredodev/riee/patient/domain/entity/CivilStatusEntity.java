package com.fredodev.riee.patient.domain.entity;

import com.fredodev.riee.patient.domain.clasifications.CivilStatusType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "civil_status")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CivilStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 50)
    private CivilStatusType status;
}
