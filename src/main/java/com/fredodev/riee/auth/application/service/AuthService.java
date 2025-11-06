package com.fredodev.riee.auth.application.service;

import com.fredodev.riee.auth.application.dto.AuthResponse;
import com.fredodev.riee.auth.application.dto.ChangePasswordRequest;
import com.fredodev.riee.auth.application.dto.LoginRequest;
import com.fredodev.riee.auth.application.dto.RegisterRequest;
import com.fredodev.riee.auth.domain.service.JwtService;
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
    private final DentistRepository dentistRepository;
    private final SpecialityRepository specialityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(@Valid RegisterRequest request) {
        var dentist = DentistEntity.builder()
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .email(request.getEmail())
                .username(request.getUsername())
                .telefono(request.getTelefono())
                .ciDentista(request.getCiDentista())
                .universidad(request.getUniversidad())
                .promocion(request.getPromocion())
                .imagenUrl(request.getImagenUrl())
                .password(passwordEncoder.encode(request.getPassword()))
                .specialities(new java.util.ArrayList<>())
                .build();

        if (request.getEspecialidadIds() != null && !request.getEspecialidadIds().isEmpty()) {
            var specialities = new java.util.ArrayList<com.fredodev.riee.dentist.domain.entity.SpecialityEntity>();
            request.getEspecialidadIds().forEach(id -> {
                var speciality = specialityRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Especialidad no encontrada: " + id));
                specialities.add(speciality);
            });
            dentist.setSpecialities(specialities);
        }

        dentistRepository.save(dentist);
        var token = jwtService.generateToken(dentist);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse login(@Valid LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        // Buscar por username o email
        var dentist = dentistRepository.findByUsernameOrEmail(request.getUsername(), request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        var token = jwtService.generateToken(dentist);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Transactional
    public void changePassword(@Valid ChangePasswordRequest request, String username) {
        var dentist = dentistRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar contraseña actual
        if (!passwordEncoder.matches(request.getCurrentPassword(), dentist.getPassword())) {
            throw new RuntimeException("Contraseña actual incorrecta");
        }

        // Verificar que las contraseñas nuevas coincidan
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Las contraseñas no coinciden");
        }

        // Actualizar contraseña
        dentist.setPassword(passwordEncoder.encode(request.getNewPassword()));
        dentistRepository.save(dentist);
    }
}
