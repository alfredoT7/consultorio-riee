package com.fredodev.riee.dentist.domain.repository;

import com.fredodev.riee.dentist.domain.entity.DentistEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DentistRepository {
    DentistEntity save(DentistEntity dentist);
    Optional<DentistEntity> findById(Long id);
    List<DentistEntity> findAll();
    void deleteById(Long id);
    DentistEntity edit(DentistEntity dentistToEdit);
}
