package com.chiops.gateway.libs.clients;

import com.chiops.gateway.libs.dtos.InvitationCodeDTO;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;

@Client("${services.invitationcode.url}/code")
public interface InvitationCodeClient {

  @Get("/get/{code}")
  @Status(HttpStatus.FOUND)
  public InvitationCodeDTO findByCode(@PathVariable String code);

  @Get("/generate")
  @Status(HttpStatus.CREATED)
  public InvitationCodeDTO generateInvitationCode();

  @Delete("/delete/{code}")
  @Status(HttpStatus.OK)
  public void deleteByCode(@PathVariable String code);

  @Post("/use/{code}")
  public InvitationCodeDTO markAsUsed(@PathVariable String code);
}
