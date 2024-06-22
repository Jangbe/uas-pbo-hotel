package com.kelompok1.hotel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "booking_services")
public class BookingService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingServiceId;

    @ManyToOne()
    @NotNull(message = "Booking tidak boleh kosong")
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne()
    @NotNull(message = "Layanan tidak boleh kosong")
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @NotNull(message = "Kuantitas tidak boleh kosong")
    @Column
    private Integer quantity;

    @Column
    private Double totalPrice;
}
