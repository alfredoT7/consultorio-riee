package com.fredodev.riee.treatment.domain.entity;
import com.fredodev.riee.dentist.domain.entity.DentistEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "dentist_treatment_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DentistTreatmentHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dentist_id", nullable = false)
    private DentistEntity dentist;

    @ManyToOne
    @JoinColumn(name = "treatment_history_id", nullable = false)
    private TreatmentHistoryEntity treatmentHistory;

    @Column(name = "fecha_asignacion")
    private LocalDateTime fechaAsignacion;

    @Column(name = "notas", length = 500)
    private String notas;

    @Column(name = "dentista_principal")
    private Boolean dentistaPrincipal;

    @PrePersist
    protected void onCreate() {
        fechaAsignacion = LocalDateTime.now();
    }
}
