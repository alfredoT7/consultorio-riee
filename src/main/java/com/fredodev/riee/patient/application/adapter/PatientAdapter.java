package com.fredodev.riee.patient.application.adapter;

import com.fredodev.riee.patient.domain.entity.PatientEntity;
import com.fredodev.riee.patient.domain.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PatientAdapter {
    private final PatientRepository patientRepository;

    public PatientAdapter(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientEntity save(PatientEntity patient) {
        return patientRepository.save(patient);
    }

    public Optional<PatientEntity> findById(Long id) {
        return patientRepository.findById(id);
    }

    public List<PatientEntity> findAll() {
        return patientRepository.findAll();
    }

    public boolean existsByCiPaciente(long ciPaciente) {
        return patientRepository.existsByCiPaciente(ciPaciente);
    }
}
