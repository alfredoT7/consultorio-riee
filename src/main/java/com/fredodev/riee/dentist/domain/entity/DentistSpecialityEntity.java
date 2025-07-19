package com.fredodev.riee.dentist.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "dentist_specialities", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"dentist_id", "speciality_id"})
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DentistSpecialityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dentist_id", nullable = false)
    private DentistEntity dentist;

    @ManyToOne
    @JoinColumn(name = "speciality_id", nullable = false)
    private SpecialityEntity speciality;

    @Column(name = "certification_date")
    private LocalDate certificationDate;

    @Column(name = "certification_institution")
    private String certificationInstitution;
}