package com.fredodev.riee.appointment.application.service;

import com.fredodev.riee.appointment.application.dto.AppointmentRequest;
import com.fredodev.riee.appointment.application.dto.AppointmentResponse;
import com.fredodev.riee.appointment.application.usecases.AppointmentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentUseCase appointmentUseCase;

    public AppointmentResponse createAppointment(AppointmentRequest request) {
        return appointmentUseCase.createAppointment(request);
    }

    public AppointmentResponse getAppointmentById(Long id) {
        return appointmentUseCase.getAppointmentById(id);
    }

    public List<AppointmentResponse> getAllAppointments() {
        return appointmentUseCase.getAllAppointments();
    }

    public AppointmentResponse updateAppointment(Long id, AppointmentRequest request) {
        return appointmentUseCase.updateAppointment(id, request);
    }

    public void deleteAppointment(Long id) {
        appointmentUseCase.deleteAppointment(id);
    }
    public List<AppointmentResponse> getAppointmentsByPatientCi(int ciPaciente) {
        return appointmentUseCase.getAppointmentsByPatientCi(ciPaciente);
    }
}