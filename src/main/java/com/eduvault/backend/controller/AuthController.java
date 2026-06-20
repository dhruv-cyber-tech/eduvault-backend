package com.eduvault.backend.controller;

import com.eduvault.backend.dto.request.LoginRequest;
import com.eduvault.backend.dto.response.ApiResponse;
import com.eduvault.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(
            @RequestBody LoginRequest request) {
        String token = authService.login(
                request.getEmail(), request.getPassword());
        return ResponseEntity.ok(
                ApiResponse.success(
                        Map.of("token", token),
                        "Login successful"));
    }

}