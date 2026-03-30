package com.fredodev.riee.auth.application.service;

import com.fredodev.riee.auth.application.dto.AuthResponse;
import com.fredodev.riee.auth.application.dto.ChangePasswordRequest;
import com.fredodev.riee.auth.application.dto.LoginRequest;
import com.fredodev.riee.auth.application.dto.RegisterRequest;
import com.fredodev.riee.auth.domain.service.JwtService;
import com.fredodev.riee.cloudinary.application.dto.CloudinaryUploadResponse;
import com.fredodev.riee.cloudinary.application.service.CloudinaryService;
import com.fredodev.riee.cloudinary.domain.exception.CloudinaryOperationException;
import com.fredodev.riee.dentist.domain.entity.DentistEntity;
import com.fredodev.riee.dentist.domain.repository.DentistRepository;
import com.fredodev.riee.dentist.domain.repository.SpecialityRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final String REGISTER_IMAGE_FOLDER = "riee/dentists";

    private final DentistRepository dentistRepository;
    private final SpecialityRepository specialityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CloudinaryService cloudinaryService;

    @Transactional
    public AuthResponse register(@Valid RegisterRequest request) {
        validateRegisterRequest(request);

        var specialities = new java.util.ArrayList<com.fredodev.riee.dentist.domain.entity.SpecialityEntity>();
        if (request.getEspecialidadIds() != null && !request.getEspecialidadIds().isEmpty()) {
            request.getEspecialidadIds().forEach(id -> {
                var speciality = specialityRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Especialidad no encontrada: " + id));
                specialities.add(speciality);
            });
        }

        String publicId = buildRegisterImagePublicId(request);
        CloudinaryUploadResponse uploadedImage = cloudinaryService.uploadImage(
                request.getImagen(),
                REGISTER_IMAGE_FOLDER,
                publicId
        );

        var dentist = DentistEntity.builder()
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .email(request.getEmail())
                .username(request.getUsername())
                .telefono(request.getTelefono())
                .ciDentista(request.getCiDentista())
                .universidad(request.getUniversidad())
                .promocion(request.getPromocion())
                .imagenUrl(uploadedImage.getUrl())
                .imagenPublicId(uploadedImage.getPublicId())
                .password(passwordEncoder.encode(request.getPassword()))
                .specialities(specialities)
                .build();

        try {
            dentistRepository.save(dentist);
        } catch (RuntimeException exception) {
            tryDeleteUploadedImage(uploadedImage.getPublicId());
            throw exception;
        }

        var token = jwtService.generateToken(dentist);
        return AuthResponse.builder()
                .id(dentist.getId())
                .nombres(dentist.getNombres())
                .apellidos(dentist.getApellidos())
                .token(token)
                .imagenUrl(dentist.getImagenUrl())
                .build();
    }

    public AuthResponse login(@Valid LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var dentist = dentistRepository.findByUsernameOrEmail(request.getUsername(), request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        var token = jwtService.generateToken(dentist);
        return AuthResponse.builder()
                .id(dentist.getId())
                .nombres(dentist.getNombres())
                .apellidos(dentist.getApellidos())
                .token(token)
                .imagenUrl(dentist.getImagenUrl())
                .build();
    }

    @Transactional
    public void changePassword(@Valid ChangePasswordRequest request, String username) {
        var dentist = dentistRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), dentist.getPassword())) {
            throw new RuntimeException("Contraseña actual incorrecta");
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Las contraseñas no coinciden");
        }
        dentist.setPassword(passwordEncoder.encode(request.getNewPassword()));
        dentistRepository.save(dentist);
    }

    private void validateRegisterRequest(RegisterRequest request) {
        if (request.getImagen() == null || request.getImagen().isEmpty()) {
            throw new CloudinaryOperationException("La imagen es obligatoria para el registro");
        }

        if (dentistRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya esta en uso");
        }

        if (dentistRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El username ya esta en uso");
        }
    }

    private String buildRegisterImagePublicId(RegisterRequest request) {
        String fullName = (request.getNombres() + "-" + request.getApellidos()).trim();
        return sanitizeForCloudinary(request.getCiDentista()) + "=" + sanitizeForCloudinary(fullName);
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

    private void tryDeleteUploadedImage(String publicId) {
        try {
            cloudinaryService.deleteImage(publicId);
        } catch (RuntimeException ignored) {
        }
    }
}
