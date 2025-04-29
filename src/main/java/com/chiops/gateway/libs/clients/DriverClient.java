package com.chiops.gateway.libs.clients;


import com.chiops.gateway.libs.dtos.DriverDTO;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.util.List;

@Secured(SecurityRule.IS_ANONYMOUS)
@Client("${services.driver.url}/driver")
public interface DriverClient {

    @Post("/create")
    public DriverDTO createDriver(@Body DriverDTO driverDTO);

    @Put("/update")
    public DriverDTO updateDriver(@Body DriverDTO driverDTO);

    @Delete("/delete/{curp}")
    public void deleteDriver(@PathVariable String curp);

    @Get("/get/{curp}")
    public DriverDTO getDriverByCurp(@PathVariable String curp);

    @Get("/getall")
    public List<DriverDTO> getAllDrivers();
}
