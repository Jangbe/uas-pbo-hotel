package com.kelompok1.hotel.model;

import java.math.BigDecimal;

import com.kelompok1.hotel.enums.RoomStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data   
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Integer roomId;

    @Column(nullable = false)
    private String roomNumber;

    @Column()
    private String roomType;

    @Column()
    private Integer capacity;

    @Column()
    private Double pricePerNight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomStatus status;

    public Room() {
        this.status = RoomStatus.Available; // default status
    }

    public Room(String roomNumber, String roomType, Integer capacity, Double pricePerNight, RoomStatus status) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.status = status;
    }
}
