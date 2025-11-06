package com.fredodev.riee.dentist.infrastructure.ports.persistence;

import com.fredodev.riee.dentist.domain.entity.DentistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaDentistRepository extends JpaRepository<DentistEntity,Long> {
    @Query("SELECT d FROM DentistEntity d LEFT JOIN FETCH d.specialities WHERE d.username = :username")
    Optional<DentistEntity> findByUsername(@Param("username") String username);

    @Query("SELECT d FROM DentistEntity d LEFT JOIN FETCH d.specialities WHERE d.ciDentista = :ciDentista")
    Optional<DentistEntity> findByCiDentista(@Param("ciDentista") Long ciDentista);

    @Query("SELECT d FROM DentistEntity d LEFT JOIN FETCH d.specialities WHERE d.telefono = :telefono")
    Optional<DentistEntity> findByTelefono(@Param("telefono") Long telefono);

    @Query("SELECT d FROM DentistEntity d LEFT JOIN FETCH d.specialities WHERE d.email = :email")
    Optional<DentistEntity> findByEmail(@Param("email") String email);

    @Query("SELECT d FROM DentistEntity d LEFT JOIN FETCH d.specialities WHERE d.username = :username OR d.email = :email")
    Optional<DentistEntity> findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByCiDentista(Long ciDentista);
    boolean existsByTelefono(Long telefono);
}

