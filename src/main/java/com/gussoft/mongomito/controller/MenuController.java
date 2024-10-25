package com.gussoft.mongomito.controller;

import com.gussoft.mongomito.business.MenuService;
import com.gussoft.mongomito.business.UserService;
import com.gussoft.mongomito.models.Menu;
import com.gussoft.mongomito.security.AuthRequest;
import com.gussoft.mongomito.security.AuthResponse;
import com.gussoft.mongomito.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<Menu>>> getMenus() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getAuthorities)
                .map(roles -> {
                    String rols = roles.stream().map(Objects::toString).collect(Collectors.joining(","));
                    String[] array = rols.split(",");
                    return service.getMenus(array);
                })
                .flatMap(fx -> Mono.just(ResponseEntity.ok(fx)));

    }
}
