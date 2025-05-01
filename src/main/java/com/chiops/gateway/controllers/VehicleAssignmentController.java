package com.chiops.gateway.controllers;

import com.chiops.gateway.libs.clients.VehicleAssignmentClient;
import com.chiops.gateway.libs.dtos.VehicleAssignmentDTO;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
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
        return vehicleAssignmentClient.findByStatus(status);
    }

    @Get(value = "/history", produces = MediaType.APPLICATION_JSON)
    public List<VehicleAssignmentDTO> assignmentsHistory() {
        return vehicleAssignmentClient.assignmentsHistory();
    }

    @Get(value = "/vin/{vin}", produces = MediaType.APPLICATION_JSON)
    public VehicleAssignmentDTO findByVin(@PathVariable String vin) {
        return vehicleAssignmentClient.findByVin(vin);
    }

    @Post(value = "/assign", produces = MediaType.APPLICATION_JSON)
    public VehicleAssignmentDTO assignVehicleToDriver(@Body VehicleAssignmentDTO vehicleAssignmentDto) {
        return vehicleAssignmentClient.assignVehicleToDriver(vehicleAssignmentDto);
    }

    @Post(value = "/release", produces = MediaType.APPLICATION_JSON)
    public VehicleAssignmentDTO releaseVehicleFromDriver(@Body VehicleAssignmentDTO vehicleAssignmentDto) {
        return vehicleAssignmentClient.releaseVehicleFromDriver(vehicleAssignmentDto);
    }

    @Put(value = "/change", produces = MediaType.APPLICATION_JSON)
    public VehicleAssignmentDTO changeDriver(@Body VehicleAssignmentDTO vehicleAssignmentDto) {
        return vehicleAssignmentClient.changeDriver(vehicleAssignmentDto);
    }
}
