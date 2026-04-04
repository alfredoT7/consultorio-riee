package com.fredodev.riee.patient.domain.repository;

import com.fredodev.riee.patient.domain.entity.PatientQuestionnaireEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientQuestionnaireRepository extends JpaRepository<PatientQuestionnaireEntity, Long> {
    Optional<PatientQuestionnaireEntity> findByPatientId(Long patientId);
}
