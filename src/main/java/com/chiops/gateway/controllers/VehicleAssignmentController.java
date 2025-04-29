package com.chiops.gateway.controllers;

import com.chiops.gateway.libs.clients.VehicleAssignmentClient;
import com.chiops.gateway.libs.dtos.VehicleAssignmentDTO;
import com.chiops.gateway.libs.exceptions.entities.ErrorResponse;
import com.chiops.gateway.libs.exceptions.exception.BadRequestException;
import com.chiops.gateway.libs.exceptions.exception.InternalServerException;
import com.chiops.gateway.libs.exceptions.exception.MethodNotAllowedException;
import com.chiops.gateway.libs.exceptions.exception.NotFoundException;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Error;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import java.util.List;

@ExecuteOn(TaskExecutors.BLOCKING)
@Controller("/vehicle/assignment")
public class VehicleAssignmentController {

    private final VehicleAssignmentClient vehicleAssignmentClient;

    public VehicleAssignmentController(VehicleAssignmentClient vehicleAssignmentClient) {
        this.vehicleAssignmentClient = vehicleAssignmentClient;
    }

    @Get(value = "/status/{status}", produces = MediaType.APPLICATION_JSON)
    public List<VehicleAssignmentDTO> findByStatus(@PathVariable String status) {
        try {
            return vehicleAssignmentClient.findByStatus(status);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when searching by status: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when searching by status: " + e.getMessage());
        }
    }

    @Get(value = "/history", produces = MediaType.APPLICATION_JSON)
    public List<VehicleAssignmentDTO> assignmentsHistory() {
        try {
            return vehicleAssignmentClient.assignmentsHistory();
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when fetching assignments history: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when fetching assignments history: " + e.getMessage());
        }
    }

    @Get(value = "/vin/{vin}", produces = MediaType.APPLICATION_JSON)
    public VehicleAssignmentDTO findByVin(@PathVariable String vin) {
        try {
            return vehicleAssignmentClient.findByVin(vin);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when searching by VIN: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when searching by VIN: " + e.getMessage());
        }
    }

    @Post(value = "/assign", produces = MediaType.APPLICATION_JSON)
    public VehicleAssignmentDTO assignVehicleToDriver(@Body VehicleAssignmentDTO vehicleAssignmentDto) {
        try {
            return vehicleAssignmentClient.assignVehicleToDriver(vehicleAssignmentDto);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when assigning vehicle to driver: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when assigning vehicle to driver: " + e.getMessage());
        }
    }

    @Post(value = "/release", produces = MediaType.APPLICATION_JSON)
    public VehicleAssignmentDTO releaseVehicleFromDriver(@Body VehicleAssignmentDTO vehicleAssignmentDto) {
        try {
            return vehicleAssignmentClient.releaseVehicleFromDriver(vehicleAssignmentDto);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when releasing vehicle from driver: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when releasing vehicle from driver: " + e.getMessage());
        }
    }

    @Put(value = "/change", produces = MediaType.APPLICATION_JSON)
    public VehicleAssignmentDTO changeDriver(@Body VehicleAssignmentDTO vehicleAssignmentDto) {
        try {
            return vehicleAssignmentClient.changeDriver(vehicleAssignmentDto);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when changing driver: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when changing driver: " + e.getMessage());
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
