package com.fredodev.riee.patient.application.usecases;

import com.fredodev.riee.patient.application.dto.PatientRequest;
import com.fredodev.riee.patient.application.dto.PhoneNumberRequest;
import com.fredodev.riee.cloudinary.application.dto.CloudinaryUploadResponse;
import com.fredodev.riee.cloudinary.application.service.CloudinaryService;
import com.fredodev.riee.patient.domain.clasifications.CivilStatusType;
import com.fredodev.riee.patient.domain.entity.CivilStatusEntity;
import com.fredodev.riee.patient.application.adapter.PatientAdapter;
import com.fredodev.riee.patient.domain.entity.PatientEntity;
import com.fredodev.riee.patient.domain.entity.PhoneNumberEntity;
import com.fredodev.riee.patient.domain.exception.PatientDomainException;
import com.fredodev.riee.patient.domain.repository.CivilStatusRepository;
import com.fredodev.riee.patient.domain.repository.PatientRepository;
import com.fredodev.riee.patient.domain.service.PatientDomainService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

@Service
public class PatientService {
    private static final String PATIENT_IMAGE_FOLDER = "riee/patients";

    private final PatientDomainService patientDomainService;
    private final PatientAdapter patientAdapter;
    private final PatientRepository patientRepository;
    private final CivilStatusRepository civilStatusRepository;
    private final CloudinaryService cloudinaryService;

    public PatientService(
            PatientDomainService patientDomainService,
            PatientAdapter patientAdapter,
            PatientRepository patientRepository,
            CivilStatusRepository civilStatusRepository,
            CloudinaryService cloudinaryService
    ) {
        this.patientDomainService = patientDomainService;
        this.patientAdapter = patientAdapter;
        this.patientRepository = patientRepository;
        this.civilStatusRepository = civilStatusRepository;
        this.cloudinaryService = cloudinaryService;
    }


    @Transactional
    public PatientEntity createPatient(@Valid PatientRequest request) {
        if (patientRepository.existsByCiPaciente(request.getCiPaciente())) {
            throw new PatientDomainException("Ya existe un paciente con el CI: " + request.getCiPaciente());
        }

        CivilStatusType civilStatusType = parseCivilStatus(request.getEstadoCivil());
        CivilStatusEntity civilStatus = civilStatusRepository.findByStatus(civilStatusType)
                .orElseThrow(() -> new PatientDomainException(
                        "No existe el estado civil configurado para: " + civilStatusType.name()
                ));

        PatientEntity patient = new PatientEntity();
        patient.setCiPaciente(request.getCiPaciente());
        patient.setEmail(request.getEmail());
        patient.setEstadoCivil(civilStatus);
        patient.setFechaNacimiento(Date.valueOf(request.getFechaNacimiento()));
        patient.setDireccion(request.getDireccion());
        patient.setOcupacion(request.getOcupacion());
        patient.setPersonaDeReferencia(request.getPersonaDeReferencia());
        patient.setNumeroPersonaRef(request.getNumeroPersonaRef() == null ? 0L : request.getNumeroPersonaRef());
        patient.setNombre(request.getNombre());
        patient.setApellido(request.getApellido());
        patient.setPhonesNumbers(mapPhoneNumbers(request.getPhonesNumbers()));

        CloudinaryUploadResponse uploadedImage = null;
        MultipartFile image = request.getImagen();
        if (image != null && !image.isEmpty()) {
            uploadedImage = cloudinaryService.uploadImage(
                    image,
                    PATIENT_IMAGE_FOLDER,
                    buildPatientImagePublicId(request.getCiPaciente(), request.getNombre(), request.getApellido())
            );
            patient.setImagen(uploadedImage.getUrl());
            patient.setImagenPublicId(uploadedImage.getPublicId());
        }

        try {
            return patientDomainService.createPatient(patient);
        } catch (RuntimeException exception) {
            if (uploadedImage != null && uploadedImage.getPublicId() != null) {
                tryDeleteImage(uploadedImage.getPublicId());
            }
            throw exception;
        }
    }

    public List<PatientEntity> getAllPatients() {
        return patientAdapter.findAll();
    }

    public PatientEntity getPatientById(Long id) {
        return patientAdapter.findById(id)
                .orElseThrow(() -> new PatientDomainException("Paciente no encontrado con ID: " + id));
    }

    private CivilStatusType parseCivilStatus(String rawCivilStatus) {
        try {
            return CivilStatusType.valueOf(rawCivilStatus.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new PatientDomainException(
                    "Estado civil invalido. Valores permitidos: SINGLE, MARRIED, DIVORCED, WIDOWED, SEPARATED"
            );
        }
    }

    private List<PhoneNumberEntity> mapPhoneNumbers(List<PhoneNumberRequest> phoneRequests) {
        return phoneRequests.stream().map(this::mapPhoneNumber).toList();
    }

    private PhoneNumberEntity mapPhoneNumber(PhoneNumberRequest phoneRequest) {
        PhoneNumberEntity phone = new PhoneNumberEntity();
        phone.setNumero(phoneRequest.getNumero());
        return phone;
    }

    private String buildPatientImagePublicId(Long ciPaciente, String nombre, String apellido) {
        return sanitizeForCloudinary(ciPaciente) + "=" + sanitizeForCloudinary(nombre + "-" + apellido);
    }

    private String sanitizeForCloudinary(Object value) {
        if (value == null) {
            return "sin-valor";
        }
        String sanitized = String.valueOf(value)
                .trim()
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-+|-+$)", "");
        return sanitized.isBlank() ? "sin-valor" : sanitized;
    }

    private void tryDeleteImage(String publicId) {
        try {
            cloudinaryService.deleteImage(publicId);
        } catch (RuntimeException ignored) {
        }
    }
}
