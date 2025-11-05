package com.fredodev.riee.dentist.infrastructure.ports.persistence;

import com.fredodev.riee.dentist.domain.entity.DentistSpecialityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDentistSpecialityRepository extends JpaRepository<DentistSpecialityEntity, Long> {

    @Modifying
    @Query("DELETE FROM DentistSpecialityEntity ds WHERE ds.dentist.id = :dentistId")
    void deleteAllByDentistId(@Param("dentistId") Long dentistId);
}

