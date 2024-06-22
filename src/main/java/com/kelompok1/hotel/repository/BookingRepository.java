package com.kelompok1.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kelompok1.hotel.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
