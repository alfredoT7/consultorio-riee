package com.fredodev.riee.appointment.domain.repository;

import com.fredodev.riee.appointment.domain.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
    AppointmentEntity save(AppointmentEntity appointmentEntity);
    AppointmentEntity findById(Long id);
    boolean existsById(Long id);
    List<AppointmentEntity> findAll();
    void deleteById(Long id);
    List<AppointmentEntity> findByCiPaciente(int ciPaciente);
}
