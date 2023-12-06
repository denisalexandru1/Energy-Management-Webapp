package com.example.monitoringmicroservice.service.impl;

import com.example.monitoringmicroservice.entity.Device;
import com.example.monitoringmicroservice.entity.Measurement;
import com.example.monitoringmicroservice.mapper.DeviceMapper;
import com.example.monitoringmicroservice.repository.DeviceRepository;
import com.example.monitoringmicroservice.repository.MeasurementRepository;
import com.example.monitoringmicroservice.service.ConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConsumptionServiceImpl implements ConsumptionService {
    private SimpMessagingTemplate template;
    private final DeviceRepository deviceRepository;
    private final MeasurementRepository measurementRepository;

    public ConsumptionServiceImpl(SimpMessagingTemplate template, DeviceRepository deviceRepository, MeasurementRepository measurementRepository) {
        this.template = template;
        this.deviceRepository = deviceRepository;
        this.measurementRepository = measurementRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void updateConsumption() {
        List<Device> devices = deviceRepository.findAll(); // Fetch all devices
        for(Device device : devices) {
            ArrayList<Measurement> lastSixMeasurements = measurementRepository.findTop6ByDeviceUuidOrderByTimestampDesc(device.uuid);
            double totalConsumption = lastSixMeasurements.get(0).value - lastSixMeasurements.get(5).value;
            if (totalConsumption > device.getMaxHourlyConsumption()) {
                StringBuilder sb = new StringBuilder();
                sb.append("{");
                sb.append("\"deviceUuid\":" + "\"" + device.uuid + "\",");
                sb.append("\"userUuid\":" + "\"" + device.userUuid + "\",");
                sb.append("\"maxHourlyConsumption\":" + "\"" + device.maxHourlyConsumption + "\",");
                sb.append("\"totalConsumption\":" + "\"" + totalConsumption + "\"");
                sb.append("}");
                System.out.println("Sent to Socket: " + sb.toString());
                template.convertAndSend("/topic/notifications", sb.toString());
            }
        }
    }
}
