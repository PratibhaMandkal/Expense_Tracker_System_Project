package com.budgettracker.controller;

import com.budgettracker.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {
    @GetMapping("/dashboard")
    public Map<String, String> dashboard(@AuthenticationPrincipal UserDetailsImpl user) {
        return Map.of(
            "message", "Welcome to your dashboard, " + user.getUsername() + "!",
            "username", user.getUsername(),
            "email", user.getEmail()
        );
    }
}
