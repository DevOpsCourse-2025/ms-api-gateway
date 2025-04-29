package com.chiops.gateway.services;

import com.chiops.gateway.libs.dtos.VehicleDTO;
import io.micronaut.http.multipart.CompletedFileUpload;

public interface VehicleImageEncodingService {
    VehicleDTO createVehicleWithImageEncoded(VehicleDTO vehicle, CompletedFileUpload imageFile);
}
