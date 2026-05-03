package com.fredodev.riee.patient.application.usecases;

import com.fredodev.riee.appointment.domain.entity.AppointmentEntity;
import com.fredodev.riee.appointment.domain.service.AppointmentDomainService;
import com.fredodev.riee.cloudinary.application.service.CloudinaryService;
import com.fredodev.riee.patient.application.adapter.PatientAdapter;
import com.fredodev.riee.patient.application.dto.PatientResponse;
import com.fredodev.riee.patient.domain.clasifications.CivilStatusType;
import com.fredodev.riee.patient.domain.entity.CivilStatusEntity;
import com.fredodev.riee.patient.domain.entity.PatientEntity;
import com.fredodev.riee.patient.domain.repository.CivilStatusRepository;
import com.fredodev.riee.patient.domain.repository.PatientClinicalInfoRepository;
import com.fredodev.riee.patient.domain.repository.PatientQuestionnaireRepository;
import com.fredodev.riee.patient.domain.repository.PatientRepository;
import com.fredodev.riee.patient.domain.service.PatientDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    private static final ZoneId ZONE = ZoneId.of("America/La_Paz");

    @Mock private PatientDomainService patientDomainService;
    @Mock private PatientAdapter patientAdapter;
    @Mock private PatientRepository patientRepository;
    @Mock private PatientClinicalInfoRepository patientClinicalInfoRepository;
    @Mock private PatientQuestionnaireRepository patientQuestionnaireRepository;
    @Mock private CivilStatusRepository civilStatusRepository;
    @Mock private CloudinaryService cloudinaryService;
    @Mock private AppointmentDomainService appointmentDomainService;

    private PatientService patientService;

    @BeforeEach
    void setUp() {
        patientService = new PatientService(
                patientDomainService,
                patientAdapter,
                patientRepository,
                patientClinicalInfoRepository,
                patientQuestionnaireRepository,
                civilStatusRepository,
                cloudinaryService,
                appointmentDomainService
        );
    }

    @Test
    void getPatientById_shouldReturnNearestUpcomingAppointment() {
        PatientEntity patient = buildPatient(1L);
        LocalDate today = LocalDate.now(ZONE);

        AppointmentEntity pastAppointment = buildAppointment(
                10L,
                Date.valueOf(today.minusDays(1)),
                Time.valueOf(LocalTime.of(9, 0)),
                "Cita pasada"
        );
        AppointmentEntity nextAppointment = buildAppointment(
                11L,
                Date.valueOf(today.plusDays(1)),
                Time.valueOf(LocalTime.of(8, 0)),
                "Próxima cita"
        );
        AppointmentEntity laterAppointment = buildAppointment(
                12L,
                Date.valueOf(today.plusDays(2)),
                Time.valueOf(LocalTime.of(8, 0)),
                "Cita posterior"
        );

        when(patientAdapter.findById(1L)).thenReturn(Optional.of(patient));
        when(appointmentDomainService.findByPatientIdOrderByFechaCitaAscHoraCitaAsc(1L))
                .thenReturn(List.of(pastAppointment, nextAppointment, laterAppointment));

        PatientResponse response = patientService.getPatientById(1L);

        assertThat(response.getProximaCita()).isNotNull();
        assertThat(response.getProximaCita().isTieneCita()).isTrue();
        assertThat(response.getProximaCita().getId()).isEqualTo(11L);
        assertThat(response.getProximaCita().getFechaCita()).isEqualTo(Date.valueOf(today.plusDays(1)));
        assertThat(response.getProximaCita().getMotivoCita()).isEqualTo("Próxima cita");
    }

    @Test
    void getPatientById_shouldReturnMessageWhenNoUpcomingAppointments() {
        PatientEntity patient = buildPatient(2L);

        when(patientAdapter.findById(2L)).thenReturn(Optional.of(patient));
        when(appointmentDomainService.findByPatientIdOrderByFechaCitaAscHoraCitaAsc(2L))
                .thenReturn(List.of());

        PatientResponse response = patientService.getPatientById(2L);

        assertThat(response.getProximaCita()).isNotNull();
        assertThat(response.getProximaCita().isTieneCita()).isFalse();
        assertThat(response.getProximaCita().getMensaje()).isEqualTo("El paciente no tiene citas programadas");
    }

    private PatientEntity buildPatient(Long id) {
        CivilStatusEntity civilStatus = new CivilStatusEntity();
        civilStatus.setId(1);
        civilStatus.setStatus(CivilStatusType.SINGLE);

        PatientEntity patient = new PatientEntity();
        patient.setId(id);
        patient.setCiPaciente(1234567L + id);
        patient.setEmail("patient" + id + "@example.com");
        patient.setEstadoCivil(civilStatus);
        patient.setFechaNacimiento(Date.valueOf(LocalDate.of(1990, 1, 1)));
        patient.setDireccion("Av. Siempre Viva");
        patient.setOcupacion("Paciente");
        patient.setPersonaDeReferencia("Referencia");
        patient.setNumeroPersonaRef(7777777L);
        patient.setNombre("Nombre");
        patient.setApellido("Apellido");
        patient.setPhonesNumbers(new java.util.ArrayList<>());
        return patient;
    }

    private AppointmentEntity buildAppointment(Long id, Date fechaCita, Time horaCita, String motivo) {
        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setId(id);
        appointment.setFechaCita(fechaCita);
        appointment.setHoraCita(horaCita);
        appointment.setMotivoCita(motivo);
        appointment.setEstadoCita("PROGRAMADA");
        appointment.setDuracionEstimada(30L);
        return appointment;
    }
}

