package com.fredodev.riee.patient.domain.repository;

import com.fredodev.riee.patient.domain.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    boolean existsByCiPaciente(long ciPaciente);
}
