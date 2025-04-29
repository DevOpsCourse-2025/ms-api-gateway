package com.chiops.gateway.libs.clients;

import com.chiops.gateway.libs.dtos.RouteDTO;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;

@Client("${services.route.url}/route")
public interface RouteClient {
    @Post("/create")
    public RouteDTO createRoute(@Body RouteDTO route);

    @Put("/update")
    public RouteDTO updateRoute(@Body RouteDTO route);

    @Delete("/delete/{vin}")
    public RouteDTO deleteRoute (@PathVariable String vin);

    @Get ("/getall")
    public Iterable<RouteDTO> getAllRoutes();

    @Get ("/get/{vin}")
    public RouteDTO getRoutesByVin(@PathVariable String vin);
}
