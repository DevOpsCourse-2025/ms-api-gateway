package com.chiops.gateway.controllers;

import com.chiops.gateway.libs.clients.InvitationCodeClient;
import com.chiops.gateway.libs.dtos.InvitationCodeDTO;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
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
        return invitationCodeClient.findByCode(code);
    }

    @Get("/generate")
    @Status(HttpStatus.CREATED)
    public InvitationCodeDTO generateInvitationCode() {
        return invitationCodeClient.generateInvitationCode();
    }

    @Delete("/delete/{code}")
    @Status(HttpStatus.OK)
    public void deleteByCode(@PathVariable String code) {
        invitationCodeClient.deleteByCode(code);
    }

    @Post("/use/{code}")
    public InvitationCodeDTO markAsUsed(@PathVariable String code) {
        return invitationCodeClient.markAsUsed(code);
    }

}
