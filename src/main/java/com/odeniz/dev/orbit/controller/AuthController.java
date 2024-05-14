package com.odeniz.dev.orbit.controller;

import com.odeniz.dev.orbit.model.AuthResponse;
import com.odeniz.dev.orbit.model.UserRegisterRequest;
import com.odeniz.dev.orbit.model.UserRequest;
import com.odeniz.dev.orbit.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login(Authentication authentication, HttpSession session){
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "auth/login";
        }
        return "redirect:/orbit";
    }

    @GetMapping("/register")
    public String register(Authentication authentication, HttpSession session){
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "auth/register";
        }
        return "redirect:/orbit";
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@ModelAttribute UserRegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@ModelAttribute UserRequest request){
        return authService.login(request);
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout"; // You can redirect to a login page with a logout message
    }
}
