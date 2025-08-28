package com.fredodev.riee.appointment.domain.entity;

import com.fredodev.riee.patient.domain.entity.PatientEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "appointments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha_cita", nullable = false)
    private Date fechaCita;
    @Column(name = "hora_cita", nullable = false)
    private Time horaCita;
    @Column(name = "motivo_cita", length = 500)
    private String motivoCita;
    @Column(name = "estado_cita", nullable = false)
    private String estadoCita;
    @Column(name = "observaciones_cita", length = 1000)
    private String observacionesCita;
    @Column(name = "duracion_estimada")
    private Long duracionEstimada;
    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;
    @ManyToOne
    @JoinColumn(name = "appointment_status_id", nullable = false)
    private AppointmentStatusEntity appointmentStatus;
}
