package com.fredodev.riee.appointment.domain.repository;

import com.fredodev.riee.appointment.domain.entity.AppointmentStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentStatusRepository extends JpaRepository<AppointmentStatusEntity, Long> {
    Optional<AppointmentStatusEntity> findByStatus(String status);

    List<AppointmentStatusEntity> findByStatusIn(Collection<String> statuses);
}

