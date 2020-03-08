package com.cdhi.controllers;

import com.cdhi.dtos.EmailDTO;
import com.cdhi.security.JWTUtil;
import com.cdhi.security.UserSS;
import com.cdhi.services.AuthService;
import com.cdhi.services.UserService;
import com.cdhi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;


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

    @PostMapping(value = "/forgot")
    public ResponseEntity<Void> forgot(@RequestBody @Valid EmailDTO objDto) {
        service.sendNewPassword(objDto.getEmail());
        return ResponseEntity.noContent().build();
    }
}
