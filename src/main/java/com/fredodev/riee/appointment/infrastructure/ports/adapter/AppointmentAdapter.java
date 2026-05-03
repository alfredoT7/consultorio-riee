package com.fredodev.riee.appointment.infrastructure.ports.adapter;

import com.fredodev.riee.appointment.domain.entity.AppointmentEntity;
import com.fredodev.riee.appointment.domain.exception.AppointmentNotFoundException;
import com.fredodev.riee.appointment.domain.repository.AppointmentRepository;
import com.fredodev.riee.appointment.infrastructure.ports.persistance.JpaAppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class AppointmentAdapter implements AppointmentRepository {
    private final JpaAppointmentRepository jpaAppointmentRepository;

    @Override
    public AppointmentEntity save(AppointmentEntity appointmentEntity) {
        return jpaAppointmentRepository.save(appointmentEntity);
    }

    @Override
    public AppointmentEntity findById(Long id) {
        return jpaAppointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    @Override
    public boolean existsById(Long id) {
        return jpaAppointmentRepository.existsById(id);
    }

    @Override
    public List<AppointmentEntity> findAll() {
        return jpaAppointmentRepository.findAll();
    }

    @Override
    public List<AppointmentEntity> findByPatientIdOrderByFechaCitaAscHoraCitaAsc(Long patientId) {
        return jpaAppointmentRepository.findByPatientIdOrderByFechaCitaAscHoraCitaAsc(patientId);
    }

    @Override
    public void deleteById(Long id) {
        jpaAppointmentRepository.deleteById(id);
    }

    @Override
    public List<AppointmentEntity> findByFechaCita(Date fechaCita) {
        return jpaAppointmentRepository.findByFechaCitaOrderByHoraCitaAsc(fechaCita);
    }

    @Override
    public List<AppointmentEntity> findByFilters(Date fromDate, Date toDate, String statusName) {
        return jpaAppointmentRepository.findAll(Sort.by(
                        Sort.Order.asc("fechaCita"),
                        Sort.Order.asc("horaCita")
                )).stream()
                .filter(appointment -> fromDate == null || !appointment.getFechaCita().before(fromDate))
                .filter(appointment -> toDate == null || !appointment.getFechaCita().after(toDate))
                .filter(appointment -> statusName == null
                        || (appointment.getAppointmentStatus() != null
                        && appointment.getAppointmentStatus().getStatus() != null
                        && appointment.getAppointmentStatus().getStatus().equalsIgnoreCase(statusName)))
                .toList();
    }
}
