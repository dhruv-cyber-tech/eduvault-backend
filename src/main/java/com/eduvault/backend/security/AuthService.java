package com.eduvault.backend.service;

import com.eduvault.backend.model.AdminUser;
import com.eduvault.backend.repository.AdminUserRepository;
import com.eduvault.backend.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminUserRepository adminUserRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public String login(String email, String password) {
        // find admin by email
        AdminUser admin = adminUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // check password
        if (!passwordEncoder.matches(password, admin.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }

        // generate and return token
        return jwtTokenProvider.generateToken(email);
    }
}