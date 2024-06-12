package com.springboot.backend.focusclubapp.focusclubbackend.controllers;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.JwtAuthenticationResponse;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.LoginRequest;
import com.springboot.backend.focusclubapp.focusclubbackend.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("Intento de inicio de sesión: " + loginRequest.getEmail());
        System.out.println("Contraseña: " + loginRequest.getPassword());

        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (Exception e) {
            System.out.println("Autentificación fallida: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Autentificación fallida");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
}
