package com.fredodev.riee.auth.infrastructure.ports.persistence;

import com.fredodev.riee.auth.domain.entity.OfficeKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOfficeKeyRepository extends JpaRepository<OfficeKeyEntity, Long> {
    boolean existsByClaveAndActivoTrue(String clave);
}
