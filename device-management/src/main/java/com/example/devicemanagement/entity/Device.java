package com.example.devicemanagement.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "device")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID uuid;
    public UUID userUuid;
    public String description;
    public String addres;
    public Float maxHourlyConsumption;
}
