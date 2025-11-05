package com.fredodev.riee.dentist.application.service;

import com.fredodev.riee.dentist.application.dto.SpecialityRequest;
import com.fredodev.riee.dentist.application.dto.SpecialityResponse;
import com.fredodev.riee.dentist.application.usecases.SpecialityUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialityService {
    private final SpecialityUseCase specialityUseCase;

    public SpecialityResponse registerSpeciality(SpecialityRequest request) {
        return specialityUseCase.registerSpeciality(request);
    }

    public SpecialityResponse updateSpeciality(Long id, SpecialityRequest request) {
        return specialityUseCase.updateSpeciality(id, request);
    }

    public SpecialityResponse getSpecialityById(Long id) {
        return specialityUseCase.getSpecialityById(id);
    }

    public List<SpecialityResponse> getAllSpecialities() {
        return specialityUseCase.getAllSpecialities();
    }

    public void deleteSpeciality(Long id) {
        specialityUseCase.deleteSpeciality(id);
    }
}

