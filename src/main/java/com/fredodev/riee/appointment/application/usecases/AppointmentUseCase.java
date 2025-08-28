package com.fredodev.riee.appointment.application.usecases;

import com.fredodev.riee.appointment.domain.service.AppointmentDomainService;
import jakarta.transaction.Transactional;

public class AppointmentUseCase {
    private final AppointmentDomainService appointmentDomainService;
    public AppointmentUseCase(AppointmentDomainService appointmentDomainService){
        this.appointmentDomainService=appointmentDomainService;
    }


}
