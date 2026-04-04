package com.fredodev.riee.patient.infrastructure.rest;

import com.fredodev.riee.patient.application.dto.PatientRequest;
import com.fredodev.riee.patient.application.dto.PatientQuestionnaireRequest;
import com.fredodev.riee.patient.application.dto.PatientQuestionnaireResponse;
import com.fredodev.riee.patient.application.usecases.PatientService;
import com.fredodev.riee.patient.domain.entity.PatientEntity;
import com.fredodev.riee.shared.api.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<PatientEntity>> createPatient(@Valid @ModelAttribute PatientRequest request) {
        PatientEntity createdPatient = patientService.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(HttpStatus.CREATED.value(), "Paciente creado correctamente", createdPatient));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PatientEntity>>> getAllPatients() {
        List<PatientEntity> patients = patientService.getAllPatients();
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Pacientes encontrados", patients));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientEntity>> getPatientById(@PathVariable Long id) {
        PatientEntity patient = patientService.getPatientById(id);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Paciente encontrado", patient));
    }

    @PostMapping("/{id}/questionnaire")
    public ResponseEntity<ApiResponse<PatientQuestionnaireResponse>> savePatientQuestionnaire(
            @PathVariable Long id,
            @Valid @RequestBody PatientQuestionnaireRequest request
    ) {
        PatientQuestionnaireResponse questionnaire = patientService.savePatientQuestionnaire(id, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(
                        HttpStatus.CREATED.value(),
                        "Cuestionario del paciente guardado correctamente",
                        questionnaire
                ));
    }

    @GetMapping("/{id}/questionnaire")
    public ResponseEntity<ApiResponse<PatientQuestionnaireResponse>> getPatientQuestionnaire(@PathVariable Long id) {
        PatientQuestionnaireResponse questionnaire = patientService.getPatientQuestionnaireByPatientId(id);
        return ResponseEntity.ok(ApiResponse.ok(
                HttpStatus.OK.value(),
                "Cuestionario del paciente encontrado",
                questionnaire
        ));
    }
}
