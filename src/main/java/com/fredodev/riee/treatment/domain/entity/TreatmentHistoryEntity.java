package com.fredodev.riee.treatment.domain.entity;

import com.fredodev.riee.patient.domain.entity.PatientEntity;
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

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;

    @ManyToMany
    @JoinTable(
            name = "treatment_history_dental_pieces",
            joinColumns = @JoinColumn(name = "treatment_history_id"),
            inverseJoinColumns = @JoinColumn(name = "dental_piece_id")
    )
    private List<DentalPieceEntity> dentalPieces;
    @Column(name = "notas_adicionales", length = 500)
    private String notasAdicionalesTratamientoPaciente;
    @Column(name = "fecha_inicio_tratamiento")
    private Date fechaInicioTratamiento;
    @Column(name = "fecha_fin_tratamiento")
    private Date fechaFinTratamiento;
    @Column(name = "precio_total_tratamiento")
    private Double precioTotalTratamiento;
    @Column(name = "costo_total_tratamiento")
    private Double costoTotalTratamiento;
    @Column(name = "descuento_total_tratamiento")
    private Double descuentoTotalTratamiento;
    @Column(name = "saldo_total_tratamiento")
    private Double saldoTotalTratamiento;
    @ManyToOne
    @JoinColumn(name = "estado_id")
    private TreatmentHistoryStatusEntity estadoTratamiento;
    @OneToMany(mappedBy = "treatmentHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TreatmentHistoryImageEntity> imagenesTratamiento;
    @ManyToOne
    @JoinColumn(name = "treatment_id")
    private TreatmentEntity treatment;

}
