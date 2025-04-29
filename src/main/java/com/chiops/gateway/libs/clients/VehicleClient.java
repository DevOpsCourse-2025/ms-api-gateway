package com.chiops.gateway.libs.clients;

import com.chiops.gateway.libs.dtos.VehicleDTO;
import com.chiops.gateway.libs.dtos.VehicleWithImageDTO;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.util.List;

@Client("${services.vehicle.url}/vehicle")
public interface VehicleClient {
    @Post(value = "/create", consumes = MediaType.APPLICATION_JSON)
    public VehicleDTO createVehicle(@Body VehicleWithImageDTO dto);

    @Put(value = "/update",
            consumes = MediaType.APPLICATION_JSON)
    public VehicleDTO updateVehicle(@Body VehicleDTO vehicle);

    @Delete("/delete/{vin}")
    public void deleteVehicle(@PathVariable String vin);

    @Get(value = "/{vin}",
            consumes = MediaType.APPLICATION_JSON)
    public VehicleDTO getVehicleByVin(@PathVariable String vin);

    @Get(value = "/model/{model}",
            consumes = MediaType.APPLICATION_JSON)
    public List<VehicleDTO> getAllVehiclesByModel(@QueryValue String model);

    @Get(value = "/getall",
            consumes = MediaType.APPLICATION_JSON)
    public List<VehicleDTO> getAllVehicles();

    @Get("/view/{filename}")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<?> viewImage(@PathVariable String filename);


}
