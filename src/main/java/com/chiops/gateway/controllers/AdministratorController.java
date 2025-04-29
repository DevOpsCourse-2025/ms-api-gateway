package com.chiops.gateway.controllers;

import com.chiops.gateway.libs.clients.AdministratorClient;
import com.chiops.gateway.libs.dtos.request.AdministratorRequestDTO;
import com.chiops.gateway.libs.dtos.response.AdministratorResponseDTO;
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
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.validation.Valid;

import java.util.List;

@ExecuteOn(TaskExecutors.BLOCKING)
@Controller("/admin")
@Secured(SecurityRule.IS_ANONYMOUS)
public class AdministratorController {

    private final AdministratorClient adminClient;

    public AdministratorController(AdministratorClient administratorClient) {
        this.adminClient = administratorClient;
    }

    @Post("/register")
    public AdministratorResponseDTO createAdministrator(@Valid @Body AdministratorRequestDTO administrator) {
        try {
            return adminClient.createAdministrator(administrator);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when registering administrator: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when registering administrator: " + e.getMessage());
        }
    }

    @Post("/login")
    public AdministratorResponseDTO signInAdministrator(@Valid @Body AdministratorRequestDTO administrator) {
        try {
            return adminClient.signInAdministrator(administrator);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when signing in: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when signing in: " + e.getMessage());
        }
    }

    @Post("/get/{email}")
    public AdministratorResponseDTO findAdministratorByEmail(@Valid @PathVariable String email) {
        try {
            return adminClient.findAdministratorByEmail(email);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when fetching administrator with email " + email + ": " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when fetching administrator with email " + email + ": " + e.getMessage());
        }
    }

    @Delete("/delete/{email}")
    public void deleteAdministratorByEmail(@Valid @PathVariable String email) {
        try {
            adminClient.deleteAdministratorByEmail(email);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when deleting administrator with email " + email + ": " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when deleting administrator with email " + email + ": " + e.getMessage());
        }
    }

    @Put("/update")
    public AdministratorResponseDTO updateAdministrator(@Valid @Body AdministratorRequestDTO administrator) {
        try {
            return adminClient.updateAdministrator(administrator);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when updating administrator: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when updating administrator: " + e.getMessage());
        }
    }

    @Get("/getall")
    public List<AdministratorResponseDTO> getAdministratorList() {
        try {
            return adminClient.getAdministratorList();
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when fetching administrator list: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when fetching administrator list: " + e.getMessage());
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
