package com.example.monitoringmicroservice.repository;

import com.example.monitoringmicroservice.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    List<Device> findAllByUserUuid(UUID uuid);
}
