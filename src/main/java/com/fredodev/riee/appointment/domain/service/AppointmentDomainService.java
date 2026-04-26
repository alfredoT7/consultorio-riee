package com.fredodev.riee.appointment.domain.service;

import com.fredodev.riee.appointment.domain.entity.AppointmentEntity;
import com.fredodev.riee.appointment.domain.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class AppointmentDomainService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentDomainService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public AppointmentEntity save(AppointmentEntity appointmentEntity) {
        return appointmentRepository.save(appointmentEntity);
    }

    public AppointmentEntity findById(Long id) {
        return appointmentRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return appointmentRepository.existsById(id);
    }

    public List<AppointmentEntity> findAll() {
        return appointmentRepository.findAll();
    }

    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }

    public List<AppointmentEntity> findByCiPaciente(int ciPaciente) {
        return appointmentRepository.findByCiPaciente(ciPaciente);
    }

    public List<AppointmentEntity> findByFechaCita(Date fechaCita) {
        return appointmentRepository.findByFechaCita(fechaCita);
    }

    public List<AppointmentEntity> findByFilters(Date fromDate, Date toDate, Integer ciPaciente, Long patientId, Long appointmentStatusId) {
        return appointmentRepository.findByFilters(fromDate, toDate, ciPaciente, patientId, appointmentStatusId);
    }
}
