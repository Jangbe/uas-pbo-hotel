package com.kelompok1.hotel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kelompok1.hotel.enums.RoomMaintenanceStatus;
import com.kelompok1.hotel.model.RoomMaintenance;
import com.kelompok1.hotel.repository.RoomMaintenanceRepository;

@Service
public class RoomMaintenanceService {

    @Autowired
    private RoomMaintenanceRepository roomRepository;

    public List<RoomMaintenance> getAllRoomMaintenances() {
        return roomRepository.findAll();
    }

    public Optional<RoomMaintenance> getRoomById(Integer id) {
        return roomRepository.findById(id);
    }

    public RoomMaintenance saveRoom(RoomMaintenance roomMaintenance) {
        return roomRepository.save(roomMaintenance);
    }

    public RoomMaintenance updateRoom(Integer id, RoomMaintenance roomDetails) {
        RoomMaintenance roomMaintenance = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room maintenance not found"));
        roomMaintenance.setRoom(roomDetails.getRoom());
        roomMaintenance.setStatus(roomDetails.getStatus());
        roomMaintenance.setDescription(roomDetails.getDescription());
        roomMaintenance.setMaintenanceDate(roomDetails.getMaintenanceDate());
        return roomRepository.save(roomMaintenance);
    }

    public RoomMaintenance updateRoomStatus(Integer id, RoomMaintenanceStatus status) {
        RoomMaintenance roomMaintenance = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room maintenance not found"));
        roomMaintenance.setStatus(status);
        return roomRepository.save(roomMaintenance);
    }

    public void deleteRoom(Integer id) {
        roomRepository.deleteById(id);
    }
}
