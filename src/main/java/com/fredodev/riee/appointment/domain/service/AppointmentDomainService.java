package com.fredodev.riee.appointment.domain.service;

import com.fredodev.riee.appointment.domain.entity.AppointmentEntity;
import com.fredodev.riee.appointment.domain.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AppointmentDomainService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentDomainService(AppointmentRepository appointmentRepository){
        this.appointmentRepository=appointmentRepository;
    }
    public AppointmentEntity save(AppointmentEntity appointmentEntity){
        return appointmentRepository.save(appointmentEntity);
    }
    public AppointmentEntity findById(Long id){
        return appointmentRepository.findById(id).orElse(null);
    }
    public boolean existsById(Long id){
        return appointmentRepository.existsById(id);
    }
    public List<AppointmentEntity> findAll(){
        return appointmentRepository.findAll();
    }
    public void deleteById(Long id){
        appointmentRepository.deleteById(id);
    }
    public List<AppointmentEntity> findByCiPaciente(int ciPaciente){
        return appointmentRepository.findByCiPaciente(ciPaciente);
    }

}
