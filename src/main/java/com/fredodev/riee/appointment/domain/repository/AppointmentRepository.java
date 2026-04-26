package com.fredodev.riee.appointment.domain.repository;

import com.fredodev.riee.appointment.domain.entity.AppointmentEntity;

import java.sql.Date;
import java.util.List;

public interface AppointmentRepository {
    AppointmentEntity save(AppointmentEntity appointmentEntity);
    AppointmentEntity findById(Long id);
    boolean existsById(Long id);
    List<AppointmentEntity> findAll();
    void deleteById(Long id);
    List<AppointmentEntity> findByCiPaciente(int ciPaciente);
    List<AppointmentEntity> findByFechaCita(Date fechaCita);
    List<AppointmentEntity> findByFilters(Date fromDate, Date toDate, Integer ciPaciente, Long patientId, Long appointmentStatusId);
}
