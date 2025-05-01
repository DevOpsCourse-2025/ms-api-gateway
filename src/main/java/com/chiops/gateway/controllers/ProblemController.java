package com.chiops.gateway.controllers;

import com.chiops.gateway.libs.clients.ProblemClient;
import com.chiops.gateway.libs.dtos.ProblemDTO;
import io.micronaut.http.annotation.*;
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
        return problemClient.assignProblem(problem);
    }

    @Put("/update")
    public ProblemDTO updateProblem(@Body ProblemDTO problem) {
        return problemClient.updateProblem(problem);
    }

    @Delete("/delete/{vin}")
    public void deleteProblem(@PathVariable String vin) {
        problemClient.deleteProblem(vin);
    }

    @Get("/get/{vin}")
    public ProblemDTO getProblemById(@PathVariable String vin) {
        return problemClient.getProblemById(vin);
    }

}