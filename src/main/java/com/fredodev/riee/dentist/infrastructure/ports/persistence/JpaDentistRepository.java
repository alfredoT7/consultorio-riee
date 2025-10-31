package com.fredodev.riee.dentist.infrastructure.ports.persistence;

import com.fredodev.riee.dentist.domain.entity.DentistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface JpaDentistRepository extends JpaRepository<DentistEntity,Long> {

}
