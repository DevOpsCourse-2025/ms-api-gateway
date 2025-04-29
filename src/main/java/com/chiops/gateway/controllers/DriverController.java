package com.chiops.gateway.controllers;

import com.chiops.gateway.libs.clients.DriverClient;
import com.chiops.gateway.libs.dtos.DriverDTO;
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

import java.util.List;

@Controller("/driver")
@ExecuteOn(TaskExecutors.BLOCKING)
@Secured(SecurityRule.IS_ANONYMOUS)
public class DriverController {

    private final DriverClient driverClient;

    public DriverController(DriverClient driverClient) {
        this.driverClient = driverClient;
    }

    @Post("/create")
    public DriverDTO createDriver(@Body DriverDTO driverDTO) {
        try {
            return driverClient.createDriver(driverDTO);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when creating driver: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when creating driver: " + e.getMessage());
        }
    }

    @Put("/update")
    public DriverDTO updateDriver(@Body DriverDTO driverDTO) {
        try {
            return driverClient.updateDriver(driverDTO);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when updating driver: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when updating driver: " + e.getMessage());
        }
    }

    @Delete("/delete/{curp}")
    public void deleteDriver(@PathVariable String curp) {
        try {
            driverClient.deleteDriver(curp);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when deleting driver: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when deleting driver: " + e.getMessage());
        }
    }

    @Get("/get/{curp}")
    public DriverDTO getDriverByCurp(@PathVariable String curp) {
        try {
            return driverClient.getDriverByCurp(curp);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when fetching driver by CURP: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when fetching driver by CURP: " + e.getMessage());
        }
    }

    @Get("/getall")
    public List<DriverDTO> getAllDrivers() {
        try {
            return driverClient.getAllDrivers();
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when fetching driver list: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when fetching driver list: " + e.getMessage());
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
