package com.fredodev.riee.appointment.infrastructure.rest;

import com.fredodev.riee.appointment.application.dto.AvailabilitySlotResponse;
import com.fredodev.riee.appointment.application.service.AppointmentService;
import com.fredodev.riee.shared.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/appointments/availability")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AppointmentAvailabilityController {

    private final AppointmentService appointmentService;

    @GetMapping("/slots")
    public ResponseEntity<ApiResponse<List<AvailabilitySlotResponse>>> getAvailableSlots(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Integer durationMinutes,
            @RequestParam(required = false) Integer slotMinutes
    ) {
        List<AvailabilitySlotResponse> slots = appointmentService.getAvailableSlots(
                Date.valueOf(date),
                durationMinutes,
                slotMinutes
        );
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Horarios disponibles encontrados", slots));
    }

    @GetMapping("/conflict")
    public ResponseEntity<ApiResponse<Boolean>> hasConflict(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time,
            @RequestParam(required = false) Integer durationMinutes,
            @RequestParam(required = false) Long excludeAppointmentId
    ) {
        boolean hasConflict = appointmentService.hasTimeConflict(
                Date.valueOf(date),
                Time.valueOf(time),
                durationMinutes,
                excludeAppointmentId
        );
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Validacion de conflicto completada", hasConflict));
    }
}

