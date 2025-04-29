package com.chiops.gateway.libs.clients;


import com.chiops.gateway.libs.dtos.request.AdministratorRequestDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import reactor.core.publisher.Mono;

@Client("${services.auth.url}/auth")
@Secured(SecurityRule.IS_ANONYMOUS)
public interface AuthClient {

    @Post("/login")
    Mono<HttpResponse<?>> login(@Body AdministratorRequestDTO dto);

    @Post("/register")
    Mono<HttpResponse<?>> register(@Body AdministratorRequestDTO dto);
}


