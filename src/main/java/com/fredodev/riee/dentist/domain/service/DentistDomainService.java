package com.fredodev.riee.dentist.domain.service;

import com.fredodev.riee.dentist.domain.entity.DentistEntity;
import com.fredodev.riee.dentist.domain.repository.DentistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DentistDomainService {

    private final DentistRepository dentistRepository;

    public DentistDomainService(DentistRepository dentistRepository) {
        this.dentistRepository = dentistRepository;
    }

    public DentistEntity saveDentist(DentistEntity dentist) {
        return dentistRepository.save(dentist);
    }

    public Optional<DentistEntity> getDentistById(Long id) {
        return dentistRepository.findById(id);
    }

    public List<DentistEntity> getAllDentist() {
        return dentistRepository.findAll();
    }

    public void deleteDentist(Long id) {
        dentistRepository.deleteById(id);
    }

    public DentistEntity editDentistDetail(DentistEntity dentistToEdit) {
        return dentistRepository.edit(dentistToEdit);
    }
}
