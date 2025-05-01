package com.chiops.gateway.controllers;

import com.chiops.gateway.libs.clients.AdministratorClient;
import com.chiops.gateway.libs.dtos.request.AdministratorRequestDTO;
import com.chiops.gateway.libs.dtos.response.AdministratorResponseDTO;
import io.micronaut.http.annotation.*;
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
        return adminClient.createAdministrator(administrator);
    }

    @Post("/login")
    public AdministratorResponseDTO signInAdministrator(@Valid @Body AdministratorRequestDTO administrator) {
        return adminClient.signInAdministrator(administrator);
    }

    @Post("/get/{email}")
    public AdministratorResponseDTO findAdministratorByEmail(@Valid @PathVariable String email) {
        return adminClient.findAdministratorByEmail(email);
    }

    @Delete("/delete/{email}")
    public void deleteAdministratorByEmail(@Valid @PathVariable String email) {
        adminClient.deleteAdministratorByEmail(email);
    }

    @Put("/update")
    public AdministratorResponseDTO updateAdministrator(@Valid @Body AdministratorRequestDTO administrator) {
        return adminClient.updateAdministrator(administrator);
    }

    @Get("/getall")
    public List<AdministratorResponseDTO> getAdministratorList() {
        return adminClient.getAdministratorList();
    }

}
