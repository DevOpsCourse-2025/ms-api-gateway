package com.chiops.gateway.controllers;

import com.chiops.gateway.libs.clients.VehicleClient;
import com.chiops.gateway.libs.dtos.VehicleDTO;
import com.chiops.gateway.libs.exceptions.entities.ErrorResponse;
import com.chiops.gateway.libs.exceptions.exception.BadRequestException;
import com.chiops.gateway.libs.exceptions.exception.InternalServerException;
import com.chiops.gateway.libs.exceptions.exception.MethodNotAllowedException;
import com.chiops.gateway.libs.exceptions.exception.NotFoundException;
import com.chiops.gateway.services.VehicleImageEncodingService;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.util.List;

@ExecuteOn(TaskExecutors.BLOCKING)
@Controller("/vehicle")
public class VehicleController {
    private final VehicleImageEncodingService vehicleImageEncodingService;
    private final VehicleClient vehicleClient;

    public VehicleController(VehicleImageEncodingService vehicleImageEncodingService, VehicleClient vehicleClient) {
        this.vehicleClient = vehicleClient;
        this.vehicleImageEncodingService = vehicleImageEncodingService;
    }

    @Post(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA)
    public VehicleDTO createVehicle(@Part("vehicle") VehicleDTO vehicle,
                                    @Part("imageFile") CompletedFileUpload imageFile) {
        try {
            return vehicleImageEncodingService.createVehicleWithImageEncoded(vehicle, imageFile);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when creating vehicle: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when creating vehicle: " + e.getMessage());
        }
    }
    
    @Put(value = "/update", consumes = MediaType.APPLICATION_JSON)
    public VehicleDTO updateVehicle(@Body VehicleDTO vehicle) {
        try {
            return vehicleClient.updateVehicle(vehicle);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when updating vehicle: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when updating vehicle: " + e.getMessage());
        }
    }

    @Delete("/delete/{vin}")
    public void deleteVehicle(@PathVariable String vin) {
        try {
            vehicleClient.deleteVehicle(vin);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when deleting vehicle: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when deleting vehicle: " + e.getMessage());
        }
    }

    @Get(value = "/get/{vin}", consumes = MediaType.APPLICATION_JSON)
    public VehicleDTO getVehicleByVin(@PathVariable String vin) {
        try {
            return vehicleClient.getVehicleByVin(vin);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when fetching vehicle by VIN: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when fetching vehicle by VIN: " + e.getMessage());
        }
    }

    @Get(value = "/model/{model}", consumes = MediaType.APPLICATION_JSON)
    public List<VehicleDTO> getAllVehiclesByModel(@QueryValue String model) {
        try {
            return vehicleClient.getAllVehiclesByModel(model);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when fetching vehicles by model: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when fetching vehicles by model: " + e.getMessage());
        }
    }

    @Get(value = "/getall", consumes = MediaType.APPLICATION_JSON)
    public List<VehicleDTO> getAllVehicles() {
        try {
            return vehicleClient.getAllVehicles();
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when fetching all vehicles: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when fetching all vehicles: " + e.getMessage());
        }
    }

    @Get("/view/{filename}")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<?> viewImage(@PathVariable String filename) {
        try {
            return vehicleClient.viewImage(filename);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when viewing image: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when viewing image: " + e.getMessage());
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
