package com.chiops.gateway.controllers;

import com.chiops.gateway.libs.clients.VehicleClient;
import com.chiops.gateway.libs.dtos.VehicleDTO;
import com.chiops.gateway.services.VehicleImageEncodingService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
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

    public VehicleController(VehicleImageEncodingService vehicleImageEncodingService,
                             VehicleClient vehicleClient) {
        this.vehicleImageEncodingService = vehicleImageEncodingService;
        this.vehicleClient = vehicleClient;
    }

    @Post(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA)
    public VehicleDTO createVehicle(@Part("vehicle") VehicleDTO vehicle,
                                    @Part("imageFile") CompletedFileUpload imageFile) {
        return vehicleImageEncodingService.createVehicleWithImageEncoded(vehicle, imageFile);
    }

    @Put(value = "/update", consumes = MediaType.APPLICATION_JSON)
    public VehicleDTO updateVehicle(@Body VehicleDTO vehicle) {
        return vehicleClient.updateVehicle(vehicle);
    }

    @Delete("/delete/{vin}")
    public void deleteVehicle(@PathVariable String vin) {
        vehicleClient.deleteVehicle(vin);
    }

    @Get(value = "/get/{vin}", consumes = MediaType.APPLICATION_JSON)
    public VehicleDTO getVehicleByVin(@PathVariable String vin) {
        return vehicleClient.getVehicleByVin(vin);
    }

    @Get(value = "/model/{model}", consumes = MediaType.APPLICATION_JSON)
    public List<VehicleDTO> getAllVehiclesByModel(@QueryValue String model) {
        return vehicleClient.getAllVehiclesByModel(model);
    }

    @Get(value = "/getall", consumes = MediaType.APPLICATION_JSON)
    public List<VehicleDTO> getAllVehicles() {
        return vehicleClient.getAllVehicles();
    }

    @Get("/view/{filename}")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<?> viewImage(@PathVariable String filename) {
        return vehicleClient.viewImage(filename);
    }


}
