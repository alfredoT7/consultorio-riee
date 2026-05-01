package com.fredodev.riee.appointment.application.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fredodev.riee.appointment.infrastructure.json.SqlTimeFlexibleDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAppointmentRequest {
    private Long patientId;
    private Date fechaCita;
    @JsonDeserialize(using = SqlTimeFlexibleDeserializer.class)
    private Time horaCita;
    private String motivoCita;
    private Long duracionEstimada;
    private String observacionesCita;
    private Long appointmentStatusId;
}
