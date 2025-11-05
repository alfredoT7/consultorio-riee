package com.fredodev.riee.dentist.infrastructure.ports.adapter;

import com.fredodev.riee.dentist.domain.entity.DentistEntity;
import com.fredodev.riee.dentist.domain.repository.DentistRepository;
import com.fredodev.riee.dentist.infrastructure.ports.persistence.JpaDentistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DentistAdapter implements DentistRepository {
    private final JpaDentistRepository jpaDentistRepository;

    @Override
    public DentistEntity save(DentistEntity dentist) {
        return jpaDentistRepository.save(dentist);
    }

    @Override
    public Optional<DentistEntity> findById(Long id) {
        return jpaDentistRepository.findById(id);
    }

    @Override
    public List<DentistEntity> findAll() {
        return jpaDentistRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        jpaDentistRepository.deleteById(id);
    }

    @Override
    public DentistEntity edit(DentistEntity dentistToEdit) {
        return jpaDentistRepository.save(dentistToEdit);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaDentistRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaDentistRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByCiDentista(Long ciDentista) {
        return jpaDentistRepository.existsByCiDentista(ciDentista);
    }

    @Override
    public Optional<DentistEntity> findByEmail(String email) {
        return jpaDentistRepository.findByEmail(email);
    }

    @Override
    public Optional<DentistEntity> findByUsername(String username) {
        return jpaDentistRepository.findByUsername(username);
    }

    @Override
    public Optional<DentistEntity> findByCiDentista(Long ciDentista) {
        return jpaDentistRepository.findByCiDentista(ciDentista);
    }

    @Override
    public Optional<DentistEntity> findByTelefono(String telefono) {
        return jpaDentistRepository.findByTelefono(telefono);
    }
}
