package com.fredodev.riee.patient.domain.service;

import com.fredodev.riee.patient.domain.entity.PatientEntity;
import com.fredodev.riee.patient.domain.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientDomainService {
    private final PatientRepository patientRepository;

    public PatientDomainService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientEntity createPatient(PatientEntity patient) {
        if (patient.getPhonesNumbers() != null) {
            patient.getPhonesNumbers().forEach(phone -> phone.setPatient(patient));
        }
        return patientRepository.save(patient);
    }
}
