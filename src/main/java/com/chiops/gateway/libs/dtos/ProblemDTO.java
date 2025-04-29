package com.chiops.gateway.libs.dtos;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;

@Introspected
@Serdeable
public class ProblemDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String vin;

    private String comment;

    public ProblemDTO() {
    }

    public ProblemDTO(String name, String description, String vin) {
        this.name = name;
        this.description = description;
        this.vin = vin;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

