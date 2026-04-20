package com.fredodev.riee.patient.infrastructure.rest;

import com.fredodev.riee.patient.application.dto.PatientRequest;
import com.fredodev.riee.patient.application.dto.PatientClinicalInfoRequest;
import com.fredodev.riee.patient.application.dto.PatientClinicalInfoResponse;
import com.fredodev.riee.patient.application.dto.PatientCompleteResponse;
import com.fredodev.riee.patient.application.dto.PatientQuestionnaireRequest;
import com.fredodev.riee.patient.application.dto.PatientQuestionnaireResponse;
import com.fredodev.riee.patient.application.dto.PatientResponse;
import com.fredodev.riee.patient.application.usecases.PatientService;
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
    public ResponseEntity<ApiResponse<PatientResponse>> createPatient(@Valid @ModelAttribute PatientRequest request) {
        PatientResponse createdPatient = patientService.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(HttpStatus.CREATED.value(), "Paciente creado correctamente", createdPatient));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<PatientResponse>> updatePatient(
            @PathVariable Long id,
            @Valid @ModelAttribute PatientRequest request
    ) {
        PatientResponse updatedPatient = patientService.updatePatient(id, request);
        return ResponseEntity.ok(ApiResponse.ok(
                HttpStatus.OK.value(),
                "Paciente actualizado correctamente",
                updatedPatient
        ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PatientResponse>>> getAllPatients() {
        List<PatientResponse> patients = patientService.getAllPatients();
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Pacientes encontrados", patients));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponse>> getPatientById(@PathVariable Long id) {
        PatientResponse patient = patientService.getPatientById(id);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Paciente encontrado", patient));
    }

    @GetMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<PatientCompleteResponse>> getPatientCompleteById(@PathVariable Long id) {
        PatientCompleteResponse patient = patientService.getPatientCompleteById(id);
        return ResponseEntity.ok(ApiResponse.ok(
                HttpStatus.OK.value(),
                "Informacion completa del paciente encontrada",
                patient
        ));
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

    @PostMapping("/{id}/clinical-info")
    public ResponseEntity<ApiResponse<PatientClinicalInfoResponse>> savePatientClinicalInfo(
            @PathVariable Long id,
            @Valid @RequestBody PatientClinicalInfoRequest request
    ) {
        PatientClinicalInfoResponse clinicalInfo = patientService.savePatientClinicalInfo(id, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(
                        HttpStatus.CREATED.value(),
                        "Informacion clinica del paciente guardada correctamente",
                        clinicalInfo
                ));
    }

    @GetMapping("/{id}/clinical-info")
    public ResponseEntity<ApiResponse<PatientClinicalInfoResponse>> getPatientClinicalInfo(@PathVariable Long id) {
        PatientClinicalInfoResponse clinicalInfo = patientService.getPatientClinicalInfoByPatientId(id);
        return ResponseEntity.ok(ApiResponse.ok(
                HttpStatus.OK.value(),
                "Informacion clinica del paciente encontrada",
                clinicalInfo
        ));
    }
}
