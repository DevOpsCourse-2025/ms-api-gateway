package com.chiops.gateway.controllers;

import com.chiops.gateway.libs.clients.ProblemClient;
import com.chiops.gateway.libs.dtos.ProblemDTO;
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
@Controller("/route/problem")
public class ProblemController {

    private final ProblemClient problemClient;

    public ProblemController(ProblemClient problemClient) {
        this.problemClient = problemClient;
    }

    @Post("/assign")
    public ProblemDTO assignProblem(@Body ProblemDTO problem) {
        try {
            return problemClient.assignProblem(problem);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when assigning problem: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when assigning problem: " + e.getMessage());
        }
    }

    @Put("/update")
    public ProblemDTO updateProblem(@Body ProblemDTO problem) {
        try {
            return problemClient.updateProblem(problem);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when updating problem: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when updating problem: " + e.getMessage());
        }
    }

    @Delete("/delete/{vin}")
    public void deleteProblem(@PathVariable String vin) {
        try {
            problemClient.deleteProblem(vin);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when deleting problem: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when deleting problem: " + e.getMessage());
        }
    }

    @Get("/get/{vin}")
    public ProblemDTO getProblemById(@PathVariable String vin) {
        try {
            return problemClient.getProblemById(vin);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request when fetching problem: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal error when fetching problem: " + e.getMessage());
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
