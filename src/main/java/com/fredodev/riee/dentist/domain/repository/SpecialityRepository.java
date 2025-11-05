package com.fredodev.riee.dentist.domain.repository;

import com.fredodev.riee.dentist.domain.entity.SpecialityEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialityRepository {
    SpecialityEntity save(SpecialityEntity speciality);
    Optional<SpecialityEntity> findById(Long id);
    List<SpecialityEntity> findAll();
    void deleteById(Long id);
    boolean existsByNombre(String nombre);
    Optional<SpecialityEntity> findByNombre(String nombre);
}

