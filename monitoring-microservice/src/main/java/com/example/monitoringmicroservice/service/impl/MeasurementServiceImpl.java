package com.example.monitoringmicroservice.service.impl;

import com.example.monitoringmicroservice.dto.MeasurementDTO;
import com.example.monitoringmicroservice.entity.Measurement;
import com.example.monitoringmicroservice.mapper.MeasurementMapper;
import com.example.monitoringmicroservice.repository.MeasurementRepository;
import com.example.monitoringmicroservice.service.MeasurementService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class MeasurementServiceImpl implements MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final MeasurementMapper measurementMapper;

    public MeasurementServiceImpl(MeasurementRepository measurementRepository, MeasurementMapper measurementMapper) {
        this.measurementRepository = measurementRepository;
        this.measurementMapper = measurementMapper;
    }

    @Override
    public MeasurementDTO registerMeasurement(String message) {
        Measurement savedMeasurement = this.measurementRepository.save(measurementMapper.toMeasurement(message));
        return measurementMapper.toDTO(savedMeasurement);
    }

    @Override
    public List<MeasurementDTO> getAllMeasurementsForDeviceByDay(UUID uuid, LocalDate date) {
        List<Measurement> measurements = measurementRepository.findByDeviceUuidAndTimestampBetween(uuid, date.atTime(0, 0, 0), date.atTime(23,59,59));
        return measurements.stream().map(measurementMapper::toDTO).collect(java.util.stream.Collectors.toList());
    }

}
