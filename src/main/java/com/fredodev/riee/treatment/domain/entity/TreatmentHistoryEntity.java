package com.fredodev.riee.treatment.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "treatment_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreatmentHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private List<DentalPieceEntity> dentalPieces;
    private String notasAdicionalesTratamientoPaciente;
    private Date fechaInicioTratamiento;
    private Date fechaFinTratamiento;
    private Double precioTotalTratamiento;
    private Double costoTotalTratamiento;
    private Double descuentoTotalTratamiento;
    private Double saldoTotalTratamiento;
    private TreatmentHistoryStatusEntity estadoTratamiento;
    private List<TreatmentHistoryImageEntity> imagenesTratamiento;

}
