package com.chiops.gateway.controllers;

import com.chiops.gateway.libs.clients.RouteClient;
import com.chiops.gateway.libs.dtos.RouteDTO;
import com.chiops.gateway.libs.exceptions.entities.ErrorResponse;
import com.chiops.gateway.libs.exceptions.exception.BadRequestException;
import com.chiops.gateway.libs.exceptions.exception.InternalServerException;
import com.chiops.gateway.libs.exceptions.exception.MethodNotAllowedException;
import com.chiops.gateway.libs.exceptions.exception.NotFoundException;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Error;
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
        try {
            return routeClient.createRoute(route);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when creating route: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when creating route: " + e.getMessage());
        }
    }

    @Put("/update")
    public RouteDTO updateRoute(@Body RouteDTO route) {
        try {
            return routeClient.updateRoute(route);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when updating route: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when updating route: " + e.getMessage());
        }
    }

    @Delete("/delete/{vin}")
    public RouteDTO deleteRoute(@PathVariable String vin) {
        try {
            return routeClient.deleteRoute(vin);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when deleting route: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when deleting route: " + e.getMessage());
        }
    }

    @Get("/getall")
    public Iterable<RouteDTO> getAllRoutes() {
        try {
            return routeClient.getAllRoutes();
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when fetching all routes: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when fetching all routes: " + e.getMessage());
        }
    }

    @Get("/get/{vin}")
    public RouteDTO getRoutesByVin(@PathVariable String vin) {
        try {
            return routeClient.getRoutesByVin(vin);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when fetching route by VIN: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when fetching route by VIN: " + e.getMessage());
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
