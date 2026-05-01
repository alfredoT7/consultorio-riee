package com.fredodev.riee.auth.infrastructure.ports.adapter;

import com.fredodev.riee.auth.domain.repository.OfficeKeyRepository;
import com.fredodev.riee.auth.infrastructure.ports.persistence.JpaOfficeKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OfficeKeyAdapter implements OfficeKeyRepository {

    private final JpaOfficeKeyRepository jpaOfficeKeyRepository;

    @Override
    public boolean existsByClaveAndActivoTrue(String clave) {
        return jpaOfficeKeyRepository.existsByClaveAndActivoTrue(clave);
    }
}
