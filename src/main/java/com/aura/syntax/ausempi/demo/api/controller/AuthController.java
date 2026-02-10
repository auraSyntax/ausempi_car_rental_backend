package com.aura.syntax.ausempi.demo.api.controller;

import com.aura.syntax.ausempi.demo.api.dto.AuthRequest;
import com.aura.syntax.ausempi.demo.api.dto.AuthResponse;
import com.aura.syntax.ausempi.demo.api.dto.RefreshTokenRequest;
import com.aura.syntax.ausempi.demo.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(service.refreshToken(request));
    }
}