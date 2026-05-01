package com.fredodev.riee.appointment.infrastructure.ports.persistance;

import com.fredodev.riee.appointment.domain.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface JpaAppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findByFechaCitaOrderByHoraCitaAsc(Date fechaCita);
}
