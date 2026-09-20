package com.money.remainder.controller;

import com.money.remainder.dto.AuthResponse;
import com.money.remainder.dto.LoginRequest;
import com.money.remainder.dto.RegisterRequest;
import com.money.remainder.entity.User;
import com.money.remainder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "database", "PostgreSQL money_reminder_db"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email is required."));
        }
        if (request.getPassword() == null || request.getPassword().length() < 6) {
            return ResponseEntity.badRequest().body(Map.of("message", "Password must be at least 6 characters."));
        }

        final String normalizedEmail = request.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmail(normalizedEmail)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "An account with this email already exists."));
        }

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName() != null ? request.getName().trim() : "User")
                .email(normalizedEmail)
                .phone(request.getPhone() != null && !request.getPhone().trim().isEmpty() ? request.getPhone().trim() : null)
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();

        user = userRepository.save(user);

        AuthResponse response = AuthResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .token("jwt_session_" + user.getId())
                .message("Registration successful.")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (request.getEmailOrPhone() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Credentials required."));
        }

        final String input = request.getEmailOrPhone().trim().toLowerCase();
        Optional<User> optionalUser = userRepository.findByEmailOrPhone(input, input);

        if (optionalUser.isEmpty() || !passwordEncoder.matches(request.getPassword(), optionalUser.get().getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid email/phone or password."));
        }

        User user = optionalUser.get();
        AuthResponse response = AuthResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .token("jwt_session_" + user.getId())
                .message("Login successful.")
                .build();

        return ResponseEntity.ok(response);
    }
}
