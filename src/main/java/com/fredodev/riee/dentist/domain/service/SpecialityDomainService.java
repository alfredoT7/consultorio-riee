package com.fredodev.riee.dentist.domain.service;

import com.fredodev.riee.dentist.domain.entity.SpecialityEntity;
import com.fredodev.riee.dentist.domain.repository.SpecialityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpecialityDomainService {
    private final SpecialityRepository specialityRepository;

    public SpecialityEntity saveSpeciality(SpecialityEntity speciality) {
        return specialityRepository.save(speciality);
    }

    public Optional<SpecialityEntity> getSpecialityById(Long id) {
        return specialityRepository.findById(id);
    }

    public List<SpecialityEntity> getAllSpecialities() {
        return specialityRepository.findAll();
    }

    public void deleteSpeciality(Long id) {
        specialityRepository.deleteById(id);
    }

    public Optional<SpecialityEntity> getSpecialityByNombre(String nombre) {
        return specialityRepository.findByNombre(nombre);
    }

    public boolean existsByNombre(String nombre) {
        return specialityRepository.existsByNombre(nombre);
    }
}

