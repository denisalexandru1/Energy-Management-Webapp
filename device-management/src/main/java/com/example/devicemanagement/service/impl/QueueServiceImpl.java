package com.example.devicemanagement.service.impl;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import com.example.devicemanagement.dto.DeviceDTO;
import com.example.devicemanagement.service.QueueService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QueueServiceImpl implements QueueService {
    @Override
    public void SendPostRequest(DeviceDTO dto) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // RabbitMQ server host
        String queueName = "device-crud-queue";
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel())
        {
            channel.queueDeclare(queueName, true, false, false, null);

            StringBuilder sb = new StringBuilder();
            sb.append("{");
            sb.append("\"type\": " + "\"POST\"" + ",");
            sb.append("\"uuid\": " + "\"" + dto.uuid + "\"" + ",");
            sb.append("\"userUuid\": " + "\"" + dto.userUuid + "\"" + ",");
            sb.append("\"maxHourlyConsumption\": " + "\"" + dto.maxHourlyConsumption + "\"");
            sb.append("}");

            channel.basicPublish("", queueName, null, sb.toString().getBytes());
            System.out.println("Sent to RabbitMQ: " + sb.toString());

        } catch (Exception e) {
            System.out.println("Connection to RabbitMQ failed");
            e.printStackTrace();
        }
    }

    @Override
    public void SendPutRequest(DeviceDTO dto) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // RabbitMQ server host
        String queueName = "device-crud-queue";
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel())
        {
            channel.queueDeclare(queueName, true, false, false, null);

            StringBuilder sb = new StringBuilder();
            sb.append("{");
            sb.append("\"type\": " + "\"PUT\"" + ",");
            sb.append("\"uuid\": " + "\"" + dto.uuid + "\"" + ",");
            sb.append("\"userUuid\": " + "\"" + dto.userUuid + "\"" + ",");
            sb.append("\"maxHourlyConsumption\": " + "\"" + dto.maxHourlyConsumption + "\"");
            sb.append("}");

            channel.basicPublish("", queueName, null, sb.toString().getBytes());
            System.out.println("Sent to RabbitMQ: " + sb.toString());

        } catch (Exception e) {
            System.out.println("Connection to RabbitMQ failed");
            e.printStackTrace();
        }
    }

    @Override
    public void SendDeleteRequest(UUID uuid){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // RabbitMQ server host
        String queueName = "device-crud-queue";
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel())
        {
            channel.queueDeclare(queueName, true, false, false, null);

            StringBuilder sb = new StringBuilder();
            sb.append("{");
            sb.append("\"type\": " + "\"DELETE\"" + ",");
            sb.append("\"uuid\": " + "\"" + uuid + "\"");
            sb.append("}");

            channel.basicPublish("", queueName, null, sb.toString().getBytes());
            System.out.println("Sent to RabbitMQ: " + sb.toString());

        } catch (Exception e) {
            System.out.println("Connection to RabbitMQ failed");
            e.printStackTrace();
        }
    }

}
