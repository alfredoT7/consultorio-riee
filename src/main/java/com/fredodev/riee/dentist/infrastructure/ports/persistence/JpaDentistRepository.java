package com.fredodev.riee.dentist.infrastructure.ports.persistence;

import com.fredodev.riee.dentist.domain.entity.DentistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaDentistRepository extends JpaRepository<DentistEntity,Long> {
    Optional<DentistEntity> findByUsername(String username);
    Optional<DentistEntity> findByCiDentista(Long ciDentista);
    Optional<DentistEntity> findByTelefono(String telefono);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByCiDentista(Long ciDentista);
    Optional<DentistEntity> findByEmail(String email);
}
