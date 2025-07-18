package com.fredodev.riee.patient.application.usecases;

import com.fredodev.riee.patient.application.adapter.PatientAdapter;
import com.fredodev.riee.patient.domain.entity.PatientEntity;
import com.fredodev.riee.patient.domain.exception.PatientDomainException;
import com.fredodev.riee.patient.domain.service.PatientDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientService {
    private final PatientDomainService patientDomainService;
    private final PatientAdapter patientAdapter;

    public PatientService(PatientDomainService patientDomainService, PatientAdapter patientAdapter) {
        this.patientDomainService = patientDomainService;
        this.patientAdapter = patientAdapter;
    }


    @Transactional
    public PatientEntity createPatient(PatientEntity patient) {
        return patientDomainService.createPatient(patient);
    }

    public List<PatientEntity> getAllPatients() {
        return patientAdapter.findAll();
    }

    public PatientEntity getPatientById(Long id) {
        return patientAdapter.findById(id)
                .orElseThrow(() -> new PatientDomainException("Paciente no encontrado con ID: " + id));
    }
}