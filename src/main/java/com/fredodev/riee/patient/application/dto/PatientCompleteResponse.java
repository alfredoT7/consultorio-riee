package com.fredodev.riee.patient.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientCompleteResponse {
    private PatientResponse patient;
    private PatientClinicalInfoResponse clinicalInfo;
    private PatientQuestionnaireResponse questionnaire;
    private boolean missingClinicalInfo;
    private boolean missingQuestionnaire;
    private List<String> missingSections;
}
