package com.fredodev.riee.patient.application.usecases;

import com.fredodev.riee.patient.application.dto.PatientClinicalInfoRequest;
import com.fredodev.riee.patient.application.dto.PatientClinicalInfoResponse;
import com.fredodev.riee.patient.application.dto.PatientCompleteResponse;
import com.fredodev.riee.patient.application.dto.PatientRequest;
import com.fredodev.riee.patient.application.dto.PatientQuestionnaireRequest;
import com.fredodev.riee.patient.application.dto.PatientQuestionnaireResponse;
import com.fredodev.riee.patient.application.dto.PatientResponse;
import com.fredodev.riee.patient.application.dto.PhoneNumberRequest;
import com.fredodev.riee.patient.application.dto.PhoneNumberResponse;
import com.fredodev.riee.patient.application.dto.CivilStatusResponse;
import com.fredodev.riee.cloudinary.application.dto.CloudinaryUploadResponse;
import com.fredodev.riee.cloudinary.application.service.CloudinaryService;
import com.fredodev.riee.patient.domain.clasifications.CivilStatusType;
import com.fredodev.riee.patient.domain.entity.CivilStatusEntity;
import com.fredodev.riee.patient.application.adapter.PatientAdapter;
import com.fredodev.riee.patient.domain.entity.PatientEntity;
import com.fredodev.riee.patient.domain.entity.PatientClinicalInfoEntity;
import com.fredodev.riee.patient.domain.entity.PatientQuestionnaireEntity;
import com.fredodev.riee.patient.domain.entity.PhoneNumberEntity;
import com.fredodev.riee.patient.domain.exception.PatientClinicalInfoNotFoundException;
import com.fredodev.riee.patient.domain.exception.PatientDomainException;
import com.fredodev.riee.patient.domain.exception.PatientNotFoundException;
import com.fredodev.riee.patient.domain.exception.PatientQuestionnaireNotFoundException;
import com.fredodev.riee.patient.domain.repository.CivilStatusRepository;
import com.fredodev.riee.patient.domain.repository.PatientClinicalInfoRepository;
import com.fredodev.riee.patient.domain.repository.PatientQuestionnaireRepository;
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
    private final PatientClinicalInfoRepository patientClinicalInfoRepository;
    private final PatientQuestionnaireRepository patientQuestionnaireRepository;
    private final CivilStatusRepository civilStatusRepository;
    private final CloudinaryService cloudinaryService;

    public PatientService(
            PatientDomainService patientDomainService,
            PatientAdapter patientAdapter,
            PatientRepository patientRepository,
            PatientClinicalInfoRepository patientClinicalInfoRepository,
            PatientQuestionnaireRepository patientQuestionnaireRepository,
            CivilStatusRepository civilStatusRepository,
            CloudinaryService cloudinaryService
    ) {
        this.patientDomainService = patientDomainService;
        this.patientAdapter = patientAdapter;
        this.patientRepository = patientRepository;
        this.patientClinicalInfoRepository = patientClinicalInfoRepository;
        this.patientQuestionnaireRepository = patientQuestionnaireRepository;
        this.civilStatusRepository = civilStatusRepository;
        this.cloudinaryService = cloudinaryService;
    }


    @Transactional
    public PatientResponse createPatient(@Valid PatientRequest request) {
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
            return mapPatientResponse(patientDomainService.createPatient(patient));
        } catch (RuntimeException exception) {
            if (uploadedImage != null && uploadedImage.getPublicId() != null) {
                tryDeleteImage(uploadedImage.getPublicId());
            }
            throw exception;
        }
    }

    @Transactional
    public PatientResponse updatePatient(Long id, @Valid PatientRequest request) {
        PatientEntity patient = getPatientEntityById(id);

        if (patientRepository.existsByCiPacienteAndIdNot(request.getCiPaciente(), id)) {
            throw new PatientDomainException("Ya existe un paciente con el CI: " + request.getCiPaciente());
        }

        CivilStatusType civilStatusType = parseCivilStatus(request.getEstadoCivil());
        CivilStatusEntity civilStatus = civilStatusRepository.findByStatus(civilStatusType)
                .orElseThrow(() -> new PatientDomainException(
                        "No existe el estado civil configurado para: " + civilStatusType.name()
                ));

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
        replacePhoneNumbers(patient, request.getPhonesNumbers());

        MultipartFile image = request.getImagen();
        if (image != null && !image.isEmpty()) {
            CloudinaryUploadResponse uploadedImage = cloudinaryService.replaceImage(
                    image,
                    PATIENT_IMAGE_FOLDER,
                    patient.getImagenPublicId()
            );
            patient.setImagen(uploadedImage.getUrl());
            patient.setImagenPublicId(uploadedImage.getPublicId());
        }

        return mapPatientResponse(patientAdapter.save(patient));
    }

    public List<PatientResponse> getAllPatients() {
        return patientAdapter.findAll().stream()
                .map(this::mapPatientResponse)
                .toList();
    }

    public PatientResponse getPatientById(Long id) {
        PatientEntity patient = getPatientEntityById(id);
        return mapPatientResponse(patient);
    }

    public PatientCompleteResponse getPatientCompleteById(Long id) {
        PatientClinicalInfoResponse clinicalInfo = null;
        PatientQuestionnaireResponse questionnaire = null;
        boolean missingClinicalInfo = false;
        boolean missingQuestionnaire = false;

        try {
            clinicalInfo = getPatientClinicalInfoByPatientId(id);
        } catch (PatientClinicalInfoNotFoundException ex) {
            missingClinicalInfo = true;
        }

        try {
            questionnaire = getPatientQuestionnaireByPatientId(id);
        } catch (PatientQuestionnaireNotFoundException ex) {
            missingQuestionnaire = true;
        }

        return PatientCompleteResponse.builder()
                .patient(getPatientById(id))
                .clinicalInfo(clinicalInfo)
                .questionnaire(questionnaire)
                .missingClinicalInfo(missingClinicalInfo)
                .missingQuestionnaire(missingQuestionnaire)
                .missingSections(buildMissingSections(missingClinicalInfo, missingQuestionnaire))
                .build();
    }

    @Transactional
    public PatientQuestionnaireResponse savePatientQuestionnaire(
            Long patientId,
            @Valid PatientQuestionnaireRequest request
    ) {
        PatientEntity patient = getPatientEntityById(patientId);
        PatientQuestionnaireEntity questionnaire = patientQuestionnaireRepository.findByPatientId(patientId)
                .orElseGet(PatientQuestionnaireEntity::new);

        questionnaire.setPatient(patient);
        questionnaire.setEstaBajoTratamientoMedicoActualmente(request.getEstaBajoTratamientoMedicoActualmente());
        questionnaire.setTomaMedicamentosRegularmente(request.getTomaMedicamentosRegularmente());
        questionnaire.setHaTenidoCirugiasImportantes(request.getHaTenidoCirugiasImportantes());
        questionnaire.setEsHipertenso(request.getEsHipertenso());
        questionnaire.setEsDiabetico(request.getEsDiabetico());
        questionnaire.setTieneProblemasCardiacos(request.getTieneProblemasCardiacos());
        questionnaire.setTieneProblemasCoagulacionOSangraFacilmente(request.getTieneProblemasCoagulacionOSangraFacilmente());
        questionnaire.setEsAlergicoAMedicamentosOAnestesia(request.getEsAlergicoAMedicamentosOAnestesia());
        questionnaire.setHaTenidoHepatitisOEnfermedadInfecciosaImportante(request.getHaTenidoHepatitisOEnfermedadInfecciosaImportante());
        questionnaire.setPadeceAsmaOProblemasRespiratorios(request.getPadeceAsmaOProblemasRespiratorios());
        questionnaire.setFuma(request.getFuma());
        questionnaire.setConsumeAlcoholFrecuentemente(request.getConsumeAlcoholFrecuentemente());
        questionnaire.setLeSangranLasEncias(request.getLeSangranLasEncias());
        questionnaire.setTieneDolorOSensibilidadDental(request.getTieneDolorOSensibilidadDental());
        questionnaire.setHaTenidoProblemasConTratamientosDentalesAnteriores(
                request.getHaTenidoProblemasConTratamientosDentalesAnteriores()
        );
        questionnaire.setEstaEmbarazadaOLactancia(request.getEstaEmbarazadaOLactancia());

        return mapQuestionnaireResponse(patientQuestionnaireRepository.save(questionnaire));
    }

    public PatientQuestionnaireResponse getPatientQuestionnaireByPatientId(Long patientId) {
        getPatientById(patientId);
        PatientQuestionnaireEntity questionnaire = patientQuestionnaireRepository.findByPatientId(patientId)
                .orElseThrow(() -> new PatientQuestionnaireNotFoundException(
                        "El paciente no tiene cuestionario registrado"
                ));
        return mapQuestionnaireResponse(questionnaire);
    }

    @Transactional
    public PatientClinicalInfoResponse savePatientClinicalInfo(
            Long patientId,
            @Valid PatientClinicalInfoRequest request
    ) {
        PatientEntity patient = getPatientEntityById(patientId);
        PatientClinicalInfoEntity clinicalInfo = patientClinicalInfoRepository.findByPatientId(patientId)
                .orElseGet(PatientClinicalInfoEntity::new);

        clinicalInfo.setPatient(patient);
        clinicalInfo.setMotivoConsulta(request.getMotivoConsulta());
        clinicalInfo.setAlergias(request.getAlergias());
        clinicalInfo.setObservaciones(request.getObservaciones());

        return mapClinicalInfoResponse(patientClinicalInfoRepository.save(clinicalInfo));
    }

    public PatientClinicalInfoResponse getPatientClinicalInfoByPatientId(Long patientId) {
        getPatientById(patientId);
        PatientClinicalInfoEntity clinicalInfo = patientClinicalInfoRepository.findByPatientId(patientId)
                .orElseThrow(() -> new PatientClinicalInfoNotFoundException(
                        "El paciente no tiene informacion clinica registrada"
                ));
        return mapClinicalInfoResponse(clinicalInfo);
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

    private void replacePhoneNumbers(PatientEntity patient, List<PhoneNumberRequest> phoneRequests) {
        patient.getPhonesNumbers().clear();
        patient.getPhonesNumbers().addAll(mapPhoneNumbers(phoneRequests));
        patient.getPhonesNumbers().forEach(phone -> phone.setPatient(patient));
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

    private List<String> buildMissingSections(boolean missingClinicalInfo, boolean missingQuestionnaire) {
        List<String> missingSections = new java.util.ArrayList<>();
        if (missingClinicalInfo) {
            missingSections.add("clinicalInfo");
        }
        if (missingQuestionnaire) {
            missingSections.add("questionnaire");
        }
        return missingSections;
    }

    private PatientEntity getPatientEntityById(Long id) {
        return patientAdapter.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Paciente no encontrado"));
    }

    private PatientResponse mapPatientResponse(PatientEntity patient) {
        return PatientResponse.builder()
                .id(patient.getId())
                .ciPaciente(patient.getCiPaciente())
                .email(patient.getEmail())
                .estadoCivil(mapCivilStatusResponse(patient.getEstadoCivil()))
                .fechaNacimiento(patient.getFechaNacimiento())
                .direccion(patient.getDireccion())
                .ocupacion(patient.getOcupacion())
                .personaDeReferencia(patient.getPersonaDeReferencia())
                .numeroPersonaRef(patient.getNumeroPersonaRef())
                .imagen(patient.getImagen())
                .imagenPublicId(patient.getImagenPublicId())
                .nombre(patient.getNombre())
                .apellido(patient.getApellido())
                .phonesNumbers(patient.getPhonesNumbers().stream()
                        .map(this::mapPhoneNumberResponse)
                        .toList())
                .build();
    }

    private CivilStatusResponse mapCivilStatusResponse(CivilStatusEntity civilStatus) {
        return CivilStatusResponse.builder()
                .id(civilStatus.getId())
                .status(civilStatus.getStatus())
                .build();
    }

    private PhoneNumberResponse mapPhoneNumberResponse(PhoneNumberEntity phoneNumber) {
        return PhoneNumberResponse.builder()
                .id(phoneNumber.getId())
                .numero(phoneNumber.getNumero())
                .build();
    }

    private PatientQuestionnaireResponse mapQuestionnaireResponse(PatientQuestionnaireEntity questionnaire) {
        return PatientQuestionnaireResponse.builder()
                .id(questionnaire.getId())
                .patientId(questionnaire.getPatient().getId())
                .estaBajoTratamientoMedicoActualmente(questionnaire.isEstaBajoTratamientoMedicoActualmente())
                .tomaMedicamentosRegularmente(questionnaire.isTomaMedicamentosRegularmente())
                .haTenidoCirugiasImportantes(questionnaire.isHaTenidoCirugiasImportantes())
                .esHipertenso(questionnaire.isEsHipertenso())
                .esDiabetico(questionnaire.isEsDiabetico())
                .tieneProblemasCardiacos(questionnaire.isTieneProblemasCardiacos())
                .tieneProblemasCoagulacionOSangraFacilmente(questionnaire.isTieneProblemasCoagulacionOSangraFacilmente())
                .esAlergicoAMedicamentosOAnestesia(questionnaire.isEsAlergicoAMedicamentosOAnestesia())
                .haTenidoHepatitisOEnfermedadInfecciosaImportante(
                        questionnaire.isHaTenidoHepatitisOEnfermedadInfecciosaImportante()
                )
                .padeceAsmaOProblemasRespiratorios(questionnaire.isPadeceAsmaOProblemasRespiratorios())
                .fuma(questionnaire.isFuma())
                .consumeAlcoholFrecuentemente(questionnaire.isConsumeAlcoholFrecuentemente())
                .leSangranLasEncias(questionnaire.isLeSangranLasEncias())
                .tieneDolorOSensibilidadDental(questionnaire.isTieneDolorOSensibilidadDental())
                .haTenidoProblemasConTratamientosDentalesAnteriores(
                        questionnaire.isHaTenidoProblemasConTratamientosDentalesAnteriores()
                )
                .estaEmbarazadaOLactancia(questionnaire.isEstaEmbarazadaOLactancia())
                .build();
    }

    private PatientClinicalInfoResponse mapClinicalInfoResponse(PatientClinicalInfoEntity clinicalInfo) {
        return PatientClinicalInfoResponse.builder()
                .id(clinicalInfo.getId())
                .patientId(clinicalInfo.getPatient().getId())
                .motivoConsulta(clinicalInfo.getMotivoConsulta())
                .alergias(clinicalInfo.getAlergias())
                .observaciones(clinicalInfo.getObservaciones())
                .build();
    }
}
