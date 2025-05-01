package com.chiops.gateway.controllers;

import com.chiops.gateway.libs.clients.RouteClient;
import com.chiops.gateway.libs.dtos.RouteDTO;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

@ExecuteOn(TaskExecutors.BLOCKING)
@Controller("/route")
public class RouteController {

    private final RouteClient routeClient;

    public RouteController(RouteClient routeClient) {
        this.routeClient = routeClient;
    }

    @Post("/create")
    public RouteDTO createRoute(@Body RouteDTO route) {
        return routeClient.createRoute(route);
    }

    @Put("/update")
    public RouteDTO updateRoute(@Body RouteDTO route) {
        return routeClient.updateRoute(route);
    }

    @Delete("/delete/{vin}")
    public RouteDTO deleteRoute(@PathVariable String vin) {
        return routeClient.deleteRoute(vin);
    }

    @Get("/getall")
    public Iterable<RouteDTO> getAllRoutes() {
        return routeClient.getAllRoutes();
    }

    @Get("/get/{vin}")
    public RouteDTO getRoutesByVin(@PathVariable String vin) {
        return routeClient.getRoutesByVin(vin);
    }
}
