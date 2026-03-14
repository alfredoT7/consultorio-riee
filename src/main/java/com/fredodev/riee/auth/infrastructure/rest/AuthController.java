package com.fredodev.riee.auth.infrastructure.rest;

import com.fredodev.riee.auth.application.dto.AuthResponse;
import com.fredodev.riee.auth.application.dto.ChangePasswordRequest;
import com.fredodev.riee.auth.application.dto.LoginRequest;
import com.fredodev.riee.auth.application.dto.RegisterRequest;
import com.fredodev.riee.auth.application.service.AuthService;
import com.fredodev.riee.shared.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @ModelAttribute RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(ApiResponse.ok(201, "Registro exitoso", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.ok(200, "Login exitoso", response));
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            Authentication authentication) {
        authService.changePassword(request, authentication.getName());
        return ResponseEntity.ok(ApiResponse.ok(200, "Contraseña actualizada exitosamente", null));
    }
}
