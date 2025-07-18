package com.fredodev.riee.patient.domain.service;

import com.fredodev.riee.patient.domain.entity.PatientEntity;
import com.fredodev.riee.patient.domain.exception.PatientDomainException;
import com.fredodev.riee.patient.domain.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class PatientDomainService {
    private final PatientRepository patientRepository;

    public PatientDomainService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientEntity createPatient(PatientEntity patient) {
        validatePatient(patient);
        if (patient.getPhonesNumbers() != null) {
            patient.getPhonesNumbers().forEach(phone -> phone.setPatient(patient));
        }
        return patientRepository.save(patient);
    }

    private void validatePatient(PatientEntity patient) {
        if (patient.getNombre() == null || patient.getNombre().trim().isEmpty()) {
            throw new PatientDomainException("El nombre del paciente es requerido");
        }

        if (patient.getApellido() == null || patient.getApellido().trim().isEmpty()) {
            throw new PatientDomainException("El apellido del paciente es requerido");
        }

        if (patient.getCiPaciente() <= 0) {
            throw new PatientDomainException("CI de paciente inválido");
        }

        if (patient.getFechaNacimiento() == null) {
            throw new PatientDomainException("La fecha de nacimiento es requerida");
        }

        if (patient.getDireccion() == null || patient.getDireccion().trim().isEmpty()) {
            throw new PatientDomainException("La dirección es requerida");
        }

        if (patient.getEstadoCivil() == null) {
            throw new PatientDomainException("El estado civil es requerido");
        }

        if (patientRepository.existsByCiPaciente(patient.getCiPaciente())) {
            throw new PatientDomainException("Ya existe un paciente con el CI: " + patient.getCiPaciente());
        }
    }
}
