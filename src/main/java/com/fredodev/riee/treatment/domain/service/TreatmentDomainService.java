package com.fredodev.riee.treatment.domain.service;

import com.fredodev.riee.treatment.domain.entity.TreatmentEntity;
import com.fredodev.riee.treatment.domain.repository.TreatmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TreatmentDomainService {
    private final TreatmentRepository treatmentRepository;

    public  TreatmentDomainService(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    public TreatmentEntity saveTreatment(TreatmentEntity treatment) {
        return treatmentRepository.save(treatment);
    }

    public Optional<TreatmentEntity> getTreatmentById(Long id) {
        return treatmentRepository.findById(id);
    }

    public List<TreatmentEntity> getAllTreatments() {
        return treatmentRepository.findAll();
    }

    public void deleteTreatment(Long id) {
        treatmentRepository.deleteById(id);
    }

    public boolean existsByNombreTratamiento(String nombreTratamiento) {
        return treatmentRepository.existsByNombreTratamiento(nombreTratamiento);
    }
}
