package com.example.devicemanagement.repository;

import com.example.devicemanagement.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {
    List<Device> findAllByUserUuid(UUID uuid);
    List<Device> deleteAllByUserUuid(UUID uuid);
}
