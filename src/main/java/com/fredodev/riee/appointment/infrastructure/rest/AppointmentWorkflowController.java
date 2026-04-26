package com.fredodev.riee.appointment.infrastructure.rest;

import com.fredodev.riee.appointment.application.dto.AppointmentRescheduleRequest;
import com.fredodev.riee.appointment.application.dto.AppointmentResponse;
import com.fredodev.riee.appointment.application.dto.AppointmentStatusActionRequest;
import com.fredodev.riee.appointment.application.service.AppointmentService;
import com.fredodev.riee.shared.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments/{id}")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AppointmentWorkflowController {

    private final AppointmentService appointmentService;

    @PatchMapping("/confirm")
    public ResponseEntity<ApiResponse<AppointmentResponse>> confirmAppointment(
            @PathVariable Long id,
            @RequestBody(required = false) AppointmentStatusActionRequest request
    ) {
        String obs = request == null ? null : request.getObservacionesCita();
        AppointmentResponse response = appointmentService.confirmAppointment(id, obs);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Cita confirmada", response));
    }

    @PatchMapping("/check-in")
    public ResponseEntity<ApiResponse<AppointmentResponse>> checkInAppointment(
            @PathVariable Long id,
            @RequestBody(required = false) AppointmentStatusActionRequest request
    ) {
        String obs = request == null ? null : request.getObservacionesCita();
        AppointmentResponse response = appointmentService.checkInAppointment(id, obs);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Paciente en espera", response));
    }

    @PatchMapping("/start")
    public ResponseEntity<ApiResponse<AppointmentResponse>> startAppointment(
            @PathVariable Long id,
            @RequestBody(required = false) AppointmentStatusActionRequest request
    ) {
        String obs = request == null ? null : request.getObservacionesCita();
        AppointmentResponse response = appointmentService.startAppointment(id, obs);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Cita iniciada", response));
    }

    @PatchMapping("/complete")
    public ResponseEntity<ApiResponse<AppointmentResponse>> completeAppointment(
            @PathVariable Long id,
            @RequestBody(required = false) AppointmentStatusActionRequest request
    ) {
        String obs = request == null ? null : request.getObservacionesCita();
        AppointmentResponse response = appointmentService.completeAppointment(id, obs);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Cita completada", response));
    }

    @PatchMapping("/cancel")
    public ResponseEntity<ApiResponse<AppointmentResponse>> cancelAppointment(
            @PathVariable Long id,
            @RequestBody(required = false) AppointmentStatusActionRequest request
    ) {
        String obs = request == null ? null : request.getObservacionesCita();
        AppointmentResponse response = appointmentService.cancelAppointment(id, obs);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Cita cancelada", response));
    }

    @PatchMapping("/no-show")
    public ResponseEntity<ApiResponse<AppointmentResponse>> noShowAppointment(
            @PathVariable Long id,
            @RequestBody(required = false) AppointmentStatusActionRequest request
    ) {
        String obs = request == null ? null : request.getObservacionesCita();
        AppointmentResponse response = appointmentService.noShowAppointment(id, obs);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Cita marcada como no asistio", response));
    }

    @PatchMapping("/reschedule")
    public ResponseEntity<ApiResponse<AppointmentResponse>> rescheduleAppointment(
            @PathVariable Long id,
            @RequestBody AppointmentRescheduleRequest request
    ) {
        AppointmentResponse response = appointmentService.rescheduleAppointment(id, request);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Cita reprogramada correctamente", response));
    }

    @PatchMapping("/status")
    public ResponseEntity<ApiResponse<AppointmentResponse>> updateAppointmentStatus(
            @PathVariable Long id,
            @RequestBody AppointmentStatusActionRequest request
    ) {
        AppointmentResponse response = appointmentService.updateAppointmentStatus(
                id,
                request.getAppointmentStatusId(),
                request.getObservacionesCita()
        );
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Estado de cita actualizado", response));
    }
}

