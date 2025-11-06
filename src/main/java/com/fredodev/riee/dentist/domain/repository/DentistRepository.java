package com.fredodev.riee.dentist.domain.repository;

import com.fredodev.riee.dentist.domain.entity.DentistEntity;

import java.util.List;
import java.util.Optional;

public interface DentistRepository {
    DentistEntity save(DentistEntity dentist);
    Optional<DentistEntity> findById(Long id);
    List<DentistEntity> findAll();
    void deleteById(Long id);

    Optional<DentistEntity> findByUsername(String username);
    Optional<DentistEntity> findByEmail(String email);
    Optional<DentistEntity> findByTelefono(Long telefono);
    Optional<DentistEntity> findByCiDentista(Long ciDentista);
    Optional<DentistEntity> findByUsernameOrEmail(String username, String email);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByCiDentista(Long ciDentista);
    boolean existsByTelefono(Long telefono);
}
