package com.chiops.gateway.libs.clients;

import com.chiops.gateway.libs.dtos.VehicleAssignmentDTO;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;

import java.util.List;

@Client ("${services.vehicle.url}/vehicle/assignment")
public interface VehicleAssignmentClient {

    @Get(value = "/status/{status}", produces = MediaType.APPLICATION_JSON)
    public List<VehicleAssignmentDTO> findByStatus(@PathVariable String status);

    @Get(value = "/history", produces = MediaType.APPLICATION_JSON)
    public List<VehicleAssignmentDTO> assignmentsHistory();

    @Get(value = "/vin/{vin}", produces = MediaType.APPLICATION_JSON)
    public VehicleAssignmentDTO findByVin(@PathVariable String vin);

    @Post(value = "/assign", produces = MediaType.APPLICATION_JSON)
    public VehicleAssignmentDTO assignVehicleToDriver(@Body VehicleAssignmentDTO vehicleAssignmentDto);

    @Post(value = "/release", produces = MediaType.APPLICATION_JSON)
    public VehicleAssignmentDTO releaseVehicleFromDriver(@Body VehicleAssignmentDTO vehicleAssignmentDto);
    @Put(value = "/change", produces = MediaType.APPLICATION_JSON)
    public VehicleAssignmentDTO changeDriver(@Body VehicleAssignmentDTO vehicleAssignmentDto);

}
