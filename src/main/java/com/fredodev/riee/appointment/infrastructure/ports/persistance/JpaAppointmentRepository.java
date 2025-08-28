package com.fredodev.riee.appointment.infrastructure.ports.persistance;

import com.fredodev.riee.appointment.domain.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaAppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    @Query("SELECT a FROM AppointmentEntity a WHERE a.patient.ciPaciente = :ciPaciente")
    List<AppointmentEntity> findByCiPaciente(@Param("ciPaciente") int ciPaciente);
}
