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
    private String nombreTratamiento;
    private String descripcion;
    private String procedimiento;
    private int semanasEstimadas;
    private int costoBaseTratamiento;
    private String notasAdicionales;
    //tipo tratmiento podria ser: correctivo, preventivo, ortodoncia,estico etc.
}
