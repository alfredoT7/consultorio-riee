package com.fredodev.riee.dentist.domain.service;

import com.fredodev.riee.dentist.domain.entity.SpecialityEntity;
import com.fredodev.riee.dentist.domain.exception.DuplicateSpecialityException;
import com.fredodev.riee.dentist.domain.exception.SpecialityNotFoundException;
import com.fredodev.riee.dentist.domain.repository.SpecialityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecialityDomainValidator {
    private final SpecialityRepository specialityRepository;

    public void validateBeforeCreate(SpecialityEntity speciality) {
        if (specialityRepository.existsByNombre(speciality.getNombre())) {
            throw new DuplicateSpecialityException("Speciality name already exists");
        }
    }

    public void validateBeforeUpdate(Long id, SpecialityEntity speciality) {
        if (id == null) throw new SpecialityNotFoundException("Id is required for update");
        if (speciality == null) throw new SpecialityNotFoundException("Speciality is null");
        specialityRepository.findById(id)
            .orElseThrow(() -> new SpecialityNotFoundException("Speciality not found"));
        var byName = specialityRepository.findByNombre(speciality.getNombre());
        if (byName.isPresent() && !byName.get().getId().equals(id)) {
            throw new DuplicateSpecialityException("Speciality name already exists for another speciality");
        }
    }
}

