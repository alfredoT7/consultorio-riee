package com.fredodev.riee.appointment.infrastructure.ports.persistance;

import com.fredodev.riee.appointment.domain.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface JpaAppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    @Query("SELECT a FROM AppointmentEntity a WHERE a.patient.ciPaciente = :ciPaciente")
    List<AppointmentEntity> findByCiPaciente(@Param("ciPaciente") int ciPaciente);

    List<AppointmentEntity> findByFechaCitaOrderByHoraCitaAsc(Date fechaCita);

    @Query("""
            SELECT a
            FROM AppointmentEntity a
            WHERE (:fromDate IS NULL OR a.fechaCita >= :fromDate)
              AND (:toDate IS NULL OR a.fechaCita <= :toDate)
              AND (:ciPaciente IS NULL OR a.patient.ciPaciente = :ciPaciente)
              AND (:patientId IS NULL OR a.patient.id = :patientId)
              AND (:appointmentStatusId IS NULL OR a.appointmentStatus.id = :appointmentStatusId)
            ORDER BY a.fechaCita ASC, a.horaCita ASC
            """)
    List<AppointmentEntity> findByFilters(
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate,
            @Param("ciPaciente") Integer ciPaciente,
            @Param("patientId") Long patientId,
            @Param("appointmentStatusId") Long appointmentStatusId
    );
}
