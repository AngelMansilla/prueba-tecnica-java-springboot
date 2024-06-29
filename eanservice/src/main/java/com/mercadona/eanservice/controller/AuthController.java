package com.mercadona.eanservice.controller;

import com.mercadona.eanservice.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public Map<String, String> authenticateUser(@RequestBody Map<String, String> loginRequest) {
        try {
            String username = loginRequest.get("username");
            String password = loginRequest.get("password");

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            String jwt = tokenProvider.generateToken(authentication);
            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            return response;
        } catch (AuthenticationException e) {
            // Debug point: print stack trace
            System.out.println("Authentication failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Invalid login credentials");
        } catch (Exception e) {
            // Debug point: print stack trace
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("An error occurred during authentication");
        }
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleAuthenticationException(RuntimeException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return response;
    }
}
