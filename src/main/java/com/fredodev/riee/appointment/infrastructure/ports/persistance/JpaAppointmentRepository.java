package com.fredodev.riee.appointment.infrastructure.ports.persistance;

import com.fredodev.riee.appointment.domain.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaAppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findByFechaCitaOrderByHoraCitaAsc(java.sql.Date fechaCita);

    @Query("""
            select a
            from AppointmentEntity a
            where a.patient.id = :patientId
            order by a.fechaCita asc, a.horaCita asc
            """)
    List<AppointmentEntity> findByPatientIdOrderByFechaCitaAscHoraCitaAsc(@Param("patientId") Long patientId);
}
