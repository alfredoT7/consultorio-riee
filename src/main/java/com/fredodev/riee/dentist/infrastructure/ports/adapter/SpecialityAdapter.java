package com.fredodev.riee.dentist.infrastructure.ports.adapter;

import com.fredodev.riee.dentist.domain.entity.SpecialityEntity;
import com.fredodev.riee.dentist.domain.repository.SpecialityRepository;
import com.fredodev.riee.dentist.infrastructure.ports.persistence.JpaSpecialityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SpecialityAdapter implements SpecialityRepository {
    private final JpaSpecialityRepository jpaSpecialityRepository;

    @Override
    public SpecialityEntity save(SpecialityEntity speciality) {
        return jpaSpecialityRepository.save(speciality);
    }

    @Override
    public Optional<SpecialityEntity> findById(Long id) {
        return jpaSpecialityRepository.findById(id);
    }

    @Override
    public List<SpecialityEntity> findAll() {
        return jpaSpecialityRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        jpaSpecialityRepository.deleteById(id);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return jpaSpecialityRepository.existsByNombre(nombre);
    }

    @Override
    public Optional<SpecialityEntity> findByNombre(String nombre) {
        return jpaSpecialityRepository.findByNombre(nombre);
    }
}

