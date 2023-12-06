package com.example.monitoringmicroservice.service;

import com.example.monitoringmicroservice.dto.MeasurementDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public interface MeasurementService {

    MeasurementDTO registerMeasurement(String message);

    List<MeasurementDTO> getAllMeasurementsForDeviceByDay(UUID uuid, LocalDate date);
}
