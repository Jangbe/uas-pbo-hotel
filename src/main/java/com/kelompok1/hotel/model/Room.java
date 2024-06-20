package com.kelompok1.hotel.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(nullable = false)
    private String roomNo;

    @Column
    private String roomType;

    @Column
    private int kapasitas;

    @Column(precision = 10, scale = 2)
    private BigDecimal harga;

    @Column(nullable = false)
    private String bookingStatus;

    public Room() {
        this.bookingStatus = "available";
    }

    public Room(String roomNo, String roomType, int kapasitas, String bookingStatus, BigDecimal harga) {
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.harga = harga;
        this.bookingStatus = bookingStatus;
    }

    // Getters and Setters
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }

    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
