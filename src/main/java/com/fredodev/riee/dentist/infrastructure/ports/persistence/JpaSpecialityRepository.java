package com.fredodev.riee.dentist.infrastructure.ports.persistence;

import com.fredodev.riee.dentist.domain.entity.SpecialityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaSpecialityRepository extends JpaRepository<SpecialityEntity, Long> {
    boolean existsByNombre(String nombre);
    Optional<SpecialityEntity> findByNombre(String nombre);
}

