package com.chiops.gateway.controllers;

import com.chiops.gateway.libs.clients.AuthClient;
import com.chiops.gateway.libs.dtos.request.AdministratorRequestDTO;
import com.chiops.gateway.libs.exceptions.entities.ErrorResponse;
import com.chiops.gateway.libs.exceptions.exception.BadRequestException;
import com.chiops.gateway.libs.exceptions.exception.InternalServerException;
import com.chiops.gateway.libs.exceptions.exception.MethodNotAllowedException;
import com.chiops.gateway.libs.exceptions.exception.NotFoundException;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Error;
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
        try {
            return authClient.login(dto);
        } catch (BadRequestException e) {
            return Mono.error(new BadRequestException("Bad request when logging in: " + e.getMessage()));
        } catch (InternalServerException e) {
            return Mono.error(new InternalServerException("Internal error when logging in: " + e.getMessage()));
        }
    }

    @Post("/register")
    public Mono<HttpResponse<?>> register(@Body AdministratorRequestDTO dto) {
        try {
            return authClient.register(dto);
        } catch (BadRequestException e) {
            return Mono.error(new BadRequestException("Bad request when registering administrator: " + e.getMessage()));
        } catch (InternalServerException e) {
            return Mono.error(new InternalServerException("Internal error when registering administrator: " + e.getMessage()));
        }
    }

    @Error(status = HttpStatus.NOT_FOUND, global = true)
    public HttpResponse<ErrorResponse> handleNotFound(HttpRequest<?> request) {
        throw new NotFoundException("Endpoint " + request.getPath() + " not found");
    }

    @Error(status = HttpStatus.METHOD_NOT_ALLOWED, global = true)
    public HttpResponse<ErrorResponse> handleMethodNotAllowed(HttpRequest<?> request) {
        throw new MethodNotAllowedException("Method " + request.getMethod() + " not allowed for " + request.getPath());
    }
}
