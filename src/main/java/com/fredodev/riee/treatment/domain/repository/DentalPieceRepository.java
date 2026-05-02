package com.fredodev.riee.treatment.domain.repository;

import com.fredodev.riee.treatment.domain.entity.DentalPieceEntity;

import java.util.List;
import java.util.Optional;

public interface DentalPieceRepository {
    Optional<DentalPieceEntity> findById(Long id);
    List<DentalPieceEntity> findAll();
    List<DentalPieceEntity> findByIdIn(List<Long> ids);
}
