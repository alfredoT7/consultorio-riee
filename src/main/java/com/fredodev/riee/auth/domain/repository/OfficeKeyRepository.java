package com.fredodev.riee.auth.domain.repository;

public interface OfficeKeyRepository {
    boolean existsByClaveAndActivoTrue(String clave);
}
