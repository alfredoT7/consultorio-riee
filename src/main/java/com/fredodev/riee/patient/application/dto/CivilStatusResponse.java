package com.fredodev.riee.patient.application.dto;

import com.fredodev.riee.patient.domain.clasifications.CivilStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CivilStatusResponse {
    private Integer id;
    private CivilStatusType status;
}
