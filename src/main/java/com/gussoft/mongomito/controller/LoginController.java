package com.gussoft.mongomito.controller;

import com.gussoft.mongomito.business.UserService;
import com.gussoft.mongomito.security.AuthRequest;
import com.gussoft.mongomito.security.AuthResponse;
import com.gussoft.mongomito.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final JwtUtil jwtUtil;
    private final UserService service;

    @PostMapping
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest request) {
        return service.searchByUser(request.getUsername())
                .map(user -> {
                    if (BCrypt.checkpw(request.getPassword(), user.getPassword())) {
                        String token = jwtUtil.generateToken(user);
                        Date expiration = jwtUtil.getExpirationToken(token);
                        return ResponseEntity.ok(new AuthResponse(token, expiration));
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                    }
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
