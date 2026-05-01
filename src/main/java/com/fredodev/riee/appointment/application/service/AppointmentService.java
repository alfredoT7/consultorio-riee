package com.fredodev.riee.appointment.application.service;

import com.fredodev.riee.appointment.application.dto.CreateAppointmentRequest;
import com.fredodev.riee.appointment.application.dto.AppointmentResponse;
import com.fredodev.riee.appointment.application.dto.AppointmentStatusResponse;
import com.fredodev.riee.appointment.application.dto.AvailabilitySlotResponse;
import com.fredodev.riee.appointment.application.dto.UpdateAppointmentRequest;
import com.fredodev.riee.appointment.application.usecases.AppointmentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentUseCase appointmentUseCase;

    public AppointmentResponse createAppointment(CreateAppointmentRequest request) {
        return appointmentUseCase.createAppointment(request);
    }

    public AppointmentResponse updateAppointment(Long id, UpdateAppointmentRequest request) {
        return appointmentUseCase.updateAppointment(id, request);
    }

    public void deleteAppointment(Long id) {
        appointmentUseCase.deleteAppointment(id);
    }

    public List<AppointmentResponse> getAppointments(Date fromDate, Date toDate, Date exactDate, String statusName) {
        return appointmentUseCase.getAppointments(fromDate, toDate, exactDate, statusName);
    }

    public List<AppointmentStatusResponse> getAppointmentStatuses() {
        return appointmentUseCase.getAppointmentStatuses();
    }

    public List<AvailabilitySlotResponse> getAvailableSlots(Date fechaCita, Integer durationMinutes) {
        return appointmentUseCase.getAvailableSlots(fechaCita, durationMinutes);
    }
}
