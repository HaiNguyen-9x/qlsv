package com.nhh.qlsv.controller;

import com.nhh.qlsv.dto.JwtAuthResponse;
import com.nhh.qlsv.dto.LoginDto;
import com.nhh.qlsv.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        JwtAuthResponse response = new JwtAuthResponse();
        response.setAccessToken(authService.login(loginDto));
        return ResponseEntity.ok(response);
    }
}
