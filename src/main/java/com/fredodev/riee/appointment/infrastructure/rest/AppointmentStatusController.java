package com.fredodev.riee.appointment.infrastructure.rest;

import com.fredodev.riee.appointment.application.dto.AppointmentStatusResponse;
import com.fredodev.riee.appointment.application.service.AppointmentService;
import com.fredodev.riee.shared.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appointment-statuses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AppointmentStatusController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentStatusResponse>>> getAllStatuses() {
        List<AppointmentStatusResponse> statuses = appointmentService.getAppointmentStatuses();
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Estados de cita encontrados", statuses));
    }
}

