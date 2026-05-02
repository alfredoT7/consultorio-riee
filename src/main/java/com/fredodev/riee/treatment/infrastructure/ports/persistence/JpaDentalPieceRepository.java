package com.fredodev.riee.treatment.infrastructure.ports.persistence;

import com.fredodev.riee.treatment.domain.entity.DentalPieceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaDentalPieceRepository extends JpaRepository<DentalPieceEntity, Long> {
    List<DentalPieceEntity> findByIdIn(List<Long> ids);
}
