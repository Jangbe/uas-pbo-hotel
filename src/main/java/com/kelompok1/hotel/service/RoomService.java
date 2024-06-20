package com.kelompok1.hotel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kelompok1.hotel.model.Room;
import com.kelompok1.hotel.repository.RoomRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room roomDetails) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
        room.setRoomNo(roomDetails.getRoomNo());
        room.setRoomType(roomDetails.getRoomType());
        room.setBookingStatus(roomDetails.getBookingStatus());
        room.setHarga(roomDetails.getHarga());
        return roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
