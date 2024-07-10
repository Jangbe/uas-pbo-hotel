package com.kelompok1.hotel.model;

import com.kelompok1.hotel.enums.RoomStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Integer roomId;

    @NotBlank(message = "Nomor ruang tidak boleh kosong")
    @Column()
    private String roomNumber;

    @NotNull(message = "Tipe ruangan tidak boleh kosong")
    @Column()
    private String roomType;

    @NotNull(message = "Kapasitas tidak boleh kosong")
    @Column()
    private Integer capacity;

    @NotNull(message = "Harga tidak boleh kosong")
    @Column()
    private Double pricePerNight;

    @NotNull(message = "Status tidak boleh kosong")
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
