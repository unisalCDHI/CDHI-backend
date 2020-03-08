package com.cdhi.controllers;

import com.cdhi.security.JWTUtil;
import com.cdhi.security.UserSS;
import com.cdhi.services.UserService;
import com.cdhi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping(value = "/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        try {
            UserSS userSS = UserService.authenticated();
            String token = jwtUtil.generateToken(userSS.getUsername());
            response.addHeader("Authorization", "Bearer " + token);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ObjectNotFoundException("Invalid Token");
        }
    }
}
