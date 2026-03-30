package com.fredodev.riee.patient.infrastructure.config;

import com.fredodev.riee.patient.domain.clasifications.CivilStatusType;
import com.fredodev.riee.patient.domain.entity.CivilStatusEntity;
import com.fredodev.riee.patient.domain.repository.CivilStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CivilStatusDataInitializer implements ApplicationRunner {

    private final CivilStatusRepository civilStatusRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        Set<CivilStatusType> existingStatuses = civilStatusRepository.findByStatusIn(
                Arrays.asList(CivilStatusType.values())
        ).stream().map(CivilStatusEntity::getStatus).collect(Collectors.toCollection(() ->
                EnumSet.noneOf(CivilStatusType.class)
        ));

        List<CivilStatusEntity> missingStatuses = Arrays.stream(CivilStatusType.values())
                .filter(status -> !existingStatuses.contains(status))
                .map(status -> {
                    CivilStatusEntity entity = new CivilStatusEntity();
                    entity.setStatus(status);
                    return entity;
                })
                .toList();

        if (!missingStatuses.isEmpty()) {
            civilStatusRepository.saveAll(missingStatuses);
        }
    }
}
