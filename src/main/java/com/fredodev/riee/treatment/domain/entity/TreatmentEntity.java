package com.fredodev.riee.treatment.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "treatments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre_tratamiento", nullable = false)
    private String nombreTratamiento;
    @Column(name = "descripcion", length = 500)
    private String descripcion;
    @Column(name = "procedimiento", length = 1000)
    private String procedimiento;
    @Column(name = "semanas_estimadas")
    private int semanasEstimadas;
    @Column(name = "costo_base_tratamiento", precision = 10, scale = 2)
    private int costoBaseTratamiento;
    @Column(name = "notas_adicionales", length = 500)
    private String notasAdicionales;
//    @ManyToOne
//    @JoinColumn(name = "tipo_tratamiento_id")
//    private TreatmentTypeEntity tipoTratamiento;
}
