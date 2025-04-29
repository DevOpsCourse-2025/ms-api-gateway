package com.chiops.gateway.libs.clients;


import com.chiops.gateway.libs.dtos.ProblemDTO;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;

@Client("${services.route.url}/route/problem")
public interface ProblemClient {

    @Post("/assign")
    public ProblemDTO assignProblem(@Body ProblemDTO problem);

    @Put("/update")
    public ProblemDTO updateProblem( @Body ProblemDTO problem);

    @Delete("/delete/{vin}")
    public void deleteProblem(@PathVariable String vin);

    @Get("/get/{vin}")
    public ProblemDTO getProblemById(@PathVariable String vin);


}
