package com.example.monitoringmicroservice.controller;

import com.example.monitoringmicroservice.dto.MeasurementDTO;
import com.example.monitoringmicroservice.service.MeasurementService;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MeasurementController {

    private final MeasurementService measurementService;
    private final RabbitAdmin rabbitAdmin;

    @Autowired
    public MeasurementController(MeasurementService measurementService, RabbitAdmin rabbitAdmin) {
        this.measurementService = measurementService;
        this.rabbitAdmin = rabbitAdmin;
        declareQueueIfNotExists("measurements-queue");
    }

    private void declareQueueIfNotExists(String queueName) {
        if (rabbitAdmin.getQueueProperties(queueName) == null || rabbitAdmin.getQueueProperties(queueName).isEmpty()) {
            rabbitAdmin.declareQueue(new Queue(queueName));
        }
    }

    @RabbitListener(queues = "measurements-queue")
    public void receiveMessage(String message) {
        MeasurementDTO measurementDTO = measurementService.registerMeasurement(message);
        System.out.println(measurementDTO);
    }

}
