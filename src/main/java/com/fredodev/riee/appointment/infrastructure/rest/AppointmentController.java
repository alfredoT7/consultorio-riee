package com.fredodev.riee.appointment.infrastructure.rest;

import com.fredodev.riee.appointment.application.dto.AppointmentResponse;
import com.fredodev.riee.appointment.application.dto.AvailabilitySlotResponse;
import com.fredodev.riee.appointment.application.dto.CreateAppointmentRequest;
import com.fredodev.riee.appointment.application.dto.UpdateAppointmentRequest;
import com.fredodev.riee.appointment.application.service.AppointmentService;
import com.fredodev.riee.shared.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointments(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String status
    ) {
        List<AppointmentResponse> appointments = appointmentService.getAppointments(
                from == null ? null : Date.valueOf(from),
                to == null ? null : Date.valueOf(to),
                date == null ? null : Date.valueOf(date),
                status
        );
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Citas encontradas", appointments));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponse>> createAppointment(@RequestBody CreateAppointmentRequest request) {
        AppointmentResponse createdAppointment = appointmentService.createAppointment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(HttpStatus.CREATED.value(), "Cita creada correctamente", createdAppointment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> updateAppointment(
            @PathVariable Long id,
            @RequestBody UpdateAppointmentRequest request
    ) {
        AppointmentResponse updatedAppointment = appointmentService.updateAppointment(id, request);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Cita actualizada correctamente", updatedAppointment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/slots")
    public ResponseEntity<ApiResponse<List<AvailabilitySlotResponse>>> getAvailableSlots(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false, defaultValue = "30") Integer duration
    ) {
        List<AvailabilitySlotResponse> slots = appointmentService.getAvailableSlots(Date.valueOf(date), duration);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Horarios disponibles encontrados", slots));
    }
}
