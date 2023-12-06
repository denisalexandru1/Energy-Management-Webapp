package com.example.monitoringmicroservice.controller;

import com.example.monitoringmicroservice.dto.DeviceDTO;
import com.example.monitoringmicroservice.dto.MeasurementDTO;
import com.example.monitoringmicroservice.repository.DeviceRepository;
import com.example.monitoringmicroservice.repository.MeasurementRepository;
import com.example.monitoringmicroservice.service.DeviceService;
import com.example.monitoringmicroservice.service.MeasurementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@org.springframework.web.bind.annotation.RestController
public class RestController {
    private final DeviceService deviceService;
    private final MeasurementService measurementService;

    public RestController(DeviceService deviceService, MeasurementService measurementService) {
        this.deviceService = deviceService;
        this.measurementService = measurementService;
    }

    @GetMapping("/devices-by-id/{id}")
    ResponseEntity<List<DeviceDTO>> getAllDevicesForUser(@PathVariable UUID uuid){
        List<DeviceDTO> devices = deviceService.getAllDevicesForUser(uuid);
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/measurements-by-day/{id}/{date}")
    ResponseEntity<List<MeasurementDTO>> getAllMeasurementsForDeviceByDate(@PathVariable("id") UUID uuid, @PathVariable("date") LocalDate date){
        List<MeasurementDTO> measurements = measurementService.getAllMeasurementsForDeviceByDay(uuid, date);
        return ResponseEntity.ok(measurements);
    }
}
