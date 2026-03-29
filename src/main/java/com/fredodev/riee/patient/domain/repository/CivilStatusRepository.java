package com.fredodev.riee.patient.domain.repository;

import com.fredodev.riee.patient.domain.clasifications.CivilStatusType;
import com.fredodev.riee.patient.domain.entity.CivilStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CivilStatusRepository extends JpaRepository<CivilStatusEntity, Integer> {
    boolean existsByStatus(CivilStatusType status);

    java.util.Optional<CivilStatusEntity> findByStatus(CivilStatusType status);

    List<CivilStatusEntity> findByStatusIn(Collection<CivilStatusType> statuses);
}
