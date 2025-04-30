package com.chiops.gateway.controllers;

import com.chiops.gateway.libs.clients.DriverClient;
import com.chiops.gateway.libs.dtos.DriverDTO;
import io.micronaut.http.annotation.*;
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
        return driverClient.createDriver(driverDTO);
    }

    @Put("/update")
    public DriverDTO updateDriver(@Body DriverDTO driverDTO) {
        return driverClient.updateDriver(driverDTO);
    }

    @Delete("/delete/{curp}")
    public void deleteDriver(@PathVariable String curp) {
        driverClient.deleteDriver(curp);
    }

    @Get("/get/{curp}")
    public DriverDTO getDriverByCurp(@PathVariable String curp) {
        return driverClient.getDriverByCurp(curp);
    }

    @Get("/getall")
    public List<DriverDTO> getAllDrivers() {
        return driverClient.getAllDrivers();
    }
}
