
package com.wififpt.api.controller;

import com.wififpt.api.dto.LoginRequest;
import com.wififpt.api.dto.LoginResponse;
import com.wififpt.api.entity.AdminUser;
import com.wififpt.api.repository.AdminUserRepository;
import com.wififpt.api.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AdminUserRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(
            AdminUserRepository adminRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request
    ) {
        AdminUser admin = adminRepository
                .findByUsername(request.username())
                .orElseThrow(this::unauthorized);

        if (!Boolean.TRUE.equals(admin.getEnabled())
                || !passwordEncoder.matches(
                request.password(),
                admin.getPasswordHash()
        )) {
            throw unauthorized();
        }

        String token = jwtService.generateToken(
                admin.getUsername()
        );

        return new LoginResponse(
                token,
                "Bearer",
                3600
        );
    }

    private ResponseStatusException unauthorized() {
        return new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Tên đăng nhập hoặc mật khẩu không đúng"
        );
    }
}
