package com.fredodev.riee.dentist.domain.service;

import com.fredodev.riee.dentist.application.dto.DentistRequest;
import com.fredodev.riee.dentist.domain.entity.DentistEntity;
import com.fredodev.riee.dentist.domain.repository.DentistRepository;
import com.fredodev.riee.dentist.domain.exception.InvalidDentistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DentistDomainValidator {

    private final DentistRepository dentistRepository;

    public void validateBeforeCreate(DentistRequest request) {
        if (dentistRepository.existsByEmail(request.getEmail())) {
            throw new InvalidDentistException("Email already in use");
        }
        if (dentistRepository.existsByUsername(request.getUsername())) {
            throw new InvalidDentistException("Username already in use");
        }
        if (request.getCiDentista() != null && dentistRepository.existsByCiDentista(request.getCiDentista())) {
            throw new InvalidDentistException("CI already in use");
        }
    }
    public void validateBeforeUpdate(Long id, DentistRequest request) {
        if (id == null) throw new InvalidDentistException("Id is required for update");
        if (request == null) throw new InvalidDentistException("Request is null");

        Optional<DentistEntity> byEmail = dentistRepository.findByEmail(request.getEmail());
        if (byEmail.isPresent() && !byEmail.get().getId().equals(id)) {
            throw new InvalidDentistException("Email already in use by another dentist");
        }

        Optional<DentistEntity> byUsername = dentistRepository.findByUsername(request.getUsername());
        if (byUsername.isPresent() && !byUsername.get().getId().equals(id)) {
            throw new InvalidDentistException("Username already in use by another dentist");
        }

        if (request.getCiDentista() != null) {
            Optional<DentistEntity> byCi = dentistRepository.findByCiDentista(request.getCiDentista());
            if (byCi.isPresent() && !byCi.get().getId().equals(id)) {
                throw new InvalidDentistException("CI already in use by another dentist");
            }
        }

        if (request.getTelefono() != null) {
            Optional<DentistEntity> byPhone = dentistRepository.findByTelefono(String.valueOf(request.getTelefono()));
            if (byPhone.isPresent() && !byPhone.get().getId().equals(id)) {
                throw new InvalidDentistException("Phone already in use by another dentist");
            }
        }
    }
}