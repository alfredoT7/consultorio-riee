package com.fredodev.riee.appointment.infrastructure.rest;

import com.fredodev.riee.appointment.application.dto.AppointmentCalendarResponse;
import com.fredodev.riee.appointment.application.dto.AppointmentFilterRequest;
import com.fredodev.riee.appointment.application.dto.AppointmentRequest;
import com.fredodev.riee.appointment.application.dto.AppointmentResponse;
import com.fredodev.riee.appointment.application.service.AppointmentService;
import com.fredodev.riee.shared.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponse>> createAppointment(@RequestBody AppointmentRequest request) {
        AppointmentResponse createdAppointment = appointmentService.createAppointment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(HttpStatus.CREATED.value(), "Cita creada correctamente", createdAppointment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> getAppointment(@PathVariable Long id) {
        AppointmentResponse appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Cita encontrada", appointment));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAllAppointments() {
        List<AppointmentResponse> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Citas encontradas", appointments));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> searchAppointments(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) Integer patientCi,
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long appointmentStatusId
    ) {
        AppointmentFilterRequest filter = AppointmentFilterRequest.builder()
                .fromDate(from == null ? null : Date.valueOf(from))
                .toDate(to == null ? null : Date.valueOf(to))
                .patientCi(patientCi)
                .patientId(patientId)
                .appointmentStatusId(appointmentStatusId)
                .build();

        List<AppointmentResponse> appointments = appointmentService.searchAppointments(filter);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Citas filtradas", appointments));
    }

    @GetMapping("/calendar")
    public ResponseEntity<ApiResponse<List<AppointmentCalendarResponse>>> getCalendarAppointments(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) Integer patientCi,
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long appointmentStatusId
    ) {
        AppointmentFilterRequest filter = AppointmentFilterRequest.builder()
                .fromDate(from == null ? null : Date.valueOf(from))
                .toDate(to == null ? null : Date.valueOf(to))
                .patientCi(patientCi)
                .patientId(patientId)
                .appointmentStatusId(appointmentStatusId)
                .build();

        List<AppointmentCalendarResponse> appointments = appointmentService.getCalendarAppointments(filter);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Eventos de calendario encontrados", appointments));
    }

    @GetMapping("/day")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointmentsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<AppointmentResponse> appointments = appointmentService.getAppointmentsByDate(Date.valueOf(date));
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Agenda del dia encontrada", appointments));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> updateAppointment(
            @PathVariable Long id,
            @RequestBody AppointmentRequest request) {
        AppointmentResponse updatedAppointment = appointmentService.updateAppointment(id, request);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Cita actualizada correctamente", updatedAppointment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Cita eliminada correctamente", null));
    }

    @GetMapping("/patient/{ciPaciente}")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointmentsByPatientCi(@PathVariable int ciPaciente) {
        List<AppointmentResponse> appointments = appointmentService.getAppointmentsByPatientCi(ciPaciente);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Citas del paciente encontradas", appointments));
    }
}