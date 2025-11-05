package com.fredodev.riee.dentist.infrastructure.ports.persistence;

import com.fredodev.riee.dentist.domain.entity.DentistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaDentistRepository extends JpaRepository<DentistEntity,Long> {
    @Query("SELECT DISTINCT d FROM DentistEntity d WHERE d.username = :username")
    Optional<DentistEntity> findByUsername(@Param("username") String username);

    @Query("SELECT DISTINCT d FROM DentistEntity d WHERE d.ciDentista = :ciDentista")
    Optional<DentistEntity> findByCiDentista(@Param("ciDentista") Long ciDentista);

    @Query("SELECT DISTINCT d FROM DentistEntity d WHERE d.telefono = :telefono")
    Optional<DentistEntity> findByTelefono(@Param("telefono") Long telefono);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByCiDentista(Long ciDentista);

    @Query("SELECT DISTINCT d FROM DentistEntity d WHERE d.email = :email")
    Optional<DentistEntity> findByEmail(@Param("email") String email);
}
