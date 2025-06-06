package com.budgettracker.controller;

import com.budgettracker.model.User;
import com.budgettracker.payload.request.LoginRequest;
import com.budgettracker.payload.request.SignupRequest;
import com.budgettracker.payload.response.JwtResponse;
import com.budgettracker.repository.UserRepository;
import com.budgettracker.security.JwtUtil;
import com.budgettracker.security.UserDetailsImpl;
import jakarta.validation.Valid;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired private AuthenticationManager authManager;
    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) throws AuthenticationException {
        Authentication auth = authManager.authenticate(
		    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
		);
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		String token = jwtUtil.generateToken(userDetails.getUsername());

		return ResponseEntity.ok(new JwtResponse(token, "Bearer", userDetails.getId(), userDetails.getUsername(), userDetails.getEmail()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest request) {
        if (userRepo.existsByUsername(request.getUsername()))
            return ResponseEntity.badRequest().body("Username is already taken!");
        if (userRepo.existsByEmail(request.getEmail()))
            return ResponseEntity.badRequest().body("Email is already in use!");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepo.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}
