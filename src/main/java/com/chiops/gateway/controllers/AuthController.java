package com.chiops.gateway.controllers;

import com.chiops.gateway.libs.clients.AuthClient;
import com.chiops.gateway.libs.dtos.request.AdministratorRequestDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;

@Controller("/auth")
@Secured(SecurityRule.IS_ANONYMOUS)
public class AuthController {

    private final AuthClient authClient;

    @Inject
    public AuthController(AuthClient authClient) {
        this.authClient = authClient;
    }

    @Post("/login")
    public Mono<HttpResponse<?>> login(@Body AdministratorRequestDTO dto) {
        return authClient.login(dto);
    }

    @Post("/register")
    public Mono<HttpResponse<?>> register(@Body AdministratorRequestDTO dto) {
        return authClient.register(dto);
    }

}
