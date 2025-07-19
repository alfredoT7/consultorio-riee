package com.fredodev.riee.treatment.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "treatment_history_statuses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentHistoryStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String nombreEstadp;

    @Column(name = "description")
    private String descripcion;

    @Column(name = "color_code")
    private String codigoColor;
}