package com.example.monitoringmicroservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class MeasurementDTO {
    public UUID uuid;
    public UUID deviceUuid;
    public LocalDateTime timestamp;
    public Double value;
}
