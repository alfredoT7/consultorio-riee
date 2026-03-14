package com.fredodev.riee.dentist.application.service;

import com.fredodev.riee.dentist.application.dto.DentistRequest;
import com.fredodev.riee.dentist.application.dto.DentistResponse;
import com.fredodev.riee.dentist.application.dto.DentistUpdateMultipartRequest;
import com.fredodev.riee.dentist.application.usecases.DentistUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DentistService {
    private final DentistUseCase dentistUseCase;

    public DentistResponse registerDentist(DentistRequest request) {
        return dentistUseCase.registerDentist(request);
    }

    public DentistResponse updateDentist(Long id, DentistRequest request) {
        return dentistUseCase.updateDentist(id, request);
    }

    public DentistResponse updateDentistWithImage(Long id, DentistUpdateMultipartRequest request) {
        return dentistUseCase.updateDentistWithImage(id, request);
    }

    public DentistResponse getDentistById(Long id) {
        return dentistUseCase.getDentistById(id);
    }

    public List<DentistResponse> getAllDentists() {
        return dentistUseCase.getAllDentists();
    }

    public void deleteDentist(Long id) {
        dentistUseCase.deleteDentist(id);
    }
}
