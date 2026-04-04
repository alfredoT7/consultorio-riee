package com.fredodev.riee.patient.domain.repository;

import com.fredodev.riee.patient.domain.entity.PatientClinicalInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientClinicalInfoRepository extends JpaRepository<PatientClinicalInfoEntity, Long> {
    Optional<PatientClinicalInfoEntity> findByPatientId(Long patientId);
}
