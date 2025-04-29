package com.chiops.gateway.services.impl;

import com.chiops.gateway.libs.clients.VehicleClient;
import com.chiops.gateway.libs.dtos.VehicleDTO;
import com.chiops.gateway.libs.dtos.VehicleWithImageDTO;
import com.chiops.gateway.libs.exceptions.exception.InternalServerException;
import com.chiops.gateway.services.VehicleImageEncodingService;

import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Singleton;

import java.util.Base64;

@Singleton
public class VehicleImageEncodingImpl implements VehicleImageEncodingService {

    private final VehicleClient vehicleClient;

    public VehicleImageEncodingImpl(VehicleClient vehicleClient) {
        this.vehicleClient = vehicleClient;
    }

    @Override
    public VehicleDTO createVehicleWithImageEncoded(VehicleDTO vehicle, CompletedFileUpload imageFile) {
        try {
            byte[] imageBytes = imageFile.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            VehicleWithImageDTO dto = new VehicleWithImageDTO();
            dto.setVehicle(vehicle);
            dto.setImageBase64(base64Image);

            return vehicleClient.createVehicle(dto);

        } catch (Exception e) {
            throw new InternalServerException("Error al procesar la imagen: " + e.getMessage());
        }
    }
}
