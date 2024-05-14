package com.odeniz.dev.orbit.service;


import com.odeniz.dev.orbit.configration.TokenManager;
import com.odeniz.dev.orbit.entity.User;
import com.odeniz.dev.orbit.model.AuthResponse;
import com.odeniz.dev.orbit.model.UserRegisterRequest;
import com.odeniz.dev.orbit.model.UserRequest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final TokenManager tokenManager;
    private final MessageSource messageSource;

    public AuthService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserService userService, TokenManager tokenManager, MessageSource messageSource) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.tokenManager = tokenManager;
        this.messageSource = messageSource;
    }

    public AuthResponse login(UserRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthResponse authResponse = new AuthResponse();

        String bearer = "Bearer " + tokenManager.generateToken(authentication);
        User user = userService.getUserByEmail(request.getEmail());
        authResponse.setAccessToken(bearer);
        authResponse.setUserId(user.getId());
        authResponse.setMessage("auth.login.success");
        return authResponse;
    }

    public ResponseEntity<AuthResponse> register(UserRegisterRequest request) {
        AuthResponse authResponse = new AuthResponse();

        if (userService.getUserByEmail(request.getEmail()) != null) {
            authResponse.setMessage(message("auth.mail.exist"));
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }

        User user = new User(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.save(user);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String bearer = "Bearer " + tokenManager.generateToken(authentication);
        authResponse.setMessage(message("auth.register.success"));
        authResponse.setUserId(user.getId());
        authResponse.setAccessToken(bearer);
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    public String registerForToken(UserRegisterRequest request) {
        if (userService.getUserByEmail(request.getEmail()) != null) {
            return loginForToken(request);
        }
        User user = new User();
        //user.setRole(Role.USER);
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(new Date());
        userService.save(user);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "Bearer " + tokenManager.generateToken(authentication);
    }

    public String loginForToken(UserRegisterRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "Bearer " + tokenManager.generateToken(authentication);
    }

    public String message(String msg) {
        return messageSource.getMessage(msg, null, LocaleContextHolder.getLocale());
    }

}

