package com.fredodev.riee.treatment.infrastructure.ports.adapter;

import com.fredodev.riee.treatment.domain.entity.DentalPieceEntity;
import com.fredodev.riee.treatment.domain.repository.DentalPieceRepository;
import com.fredodev.riee.treatment.infrastructure.ports.persistence.JpaDentalPieceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DentalPieceAdapter implements DentalPieceRepository {

    private final JpaDentalPieceRepository jpaRepository;

    @Override
    public Optional<DentalPieceEntity> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<DentalPieceEntity> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<DentalPieceEntity> findByIdIn(List<Long> ids) {
        return jpaRepository.findByIdIn(ids);
    }
}
