package com.kelompok1.hotel.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.kelompok1.hotel.enums.BookingStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @NotNull(message = "Tamu tidak boleh kosong")
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @ManyToOne
    @NotNull(message = "Ruangan tidak boleh kosong")
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "Tanggal cek in tidak boleh kosong")
    @Column(nullable = true)
    private Date checkInDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(nullable = true)
    private Date checkOutDate;

    @Column(nullable = true)
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column()
    @NotNull(message = "Status tidak boleh kosong")
    private BookingStatus status;

    @Column()
    private Date createdAt;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    List<BookingService> bookingServices;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Payment> payments;

    public Booking(){
        this.createdAt = new Date();
    }
}