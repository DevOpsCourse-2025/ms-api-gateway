package com.chiops.gateway.controllers;

import com.chiops.gateway.libs.clients.InvitationCodeClient;
import com.chiops.gateway.libs.dtos.InvitationCodeDTO;
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

@Controller("/code")
@ExecuteOn(TaskExecutors.BLOCKING)
@Secured(SecurityRule.IS_ANONYMOUS)
public class InvitationCodeController {

    private final InvitationCodeClient invitationCodeClient;

    public InvitationCodeController(InvitationCodeClient invitationCodeClient) {
        this.invitationCodeClient = invitationCodeClient;
    }

    @Get("/get/{code}")
    @Status(HttpStatus.FOUND)
    public InvitationCodeDTO findByCode(@PathVariable String code) {
        try {
            return invitationCodeClient.findByCode(code);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when fetching code: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when fetching code: " + e.getMessage());
        }
    }

    @Get("/generate")
    @Status(HttpStatus.CREATED)
    public InvitationCodeDTO generateInvitationCode() {
        try {
            return invitationCodeClient.generateInvitationCode();
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when generating invitation code: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when generating invitation code: " + e.getMessage());
        }
    }

    @Delete("/delete/{code}")
    @Status(HttpStatus.OK)
    public void deleteByCode(@PathVariable String code) {
        try {
            invitationCodeClient.deleteByCode(code);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when deleting code: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when deleting code: " + e.getMessage());
        }
    }

    @Post("/use/{code}")
    public InvitationCodeDTO markAsUsed(@PathVariable String code) {
        try {
            return invitationCodeClient.markAsUsed(code);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when marking code as used: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when marking code as used: " + e.getMessage());
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
