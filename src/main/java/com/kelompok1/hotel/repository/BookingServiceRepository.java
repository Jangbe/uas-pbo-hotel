package com.kelompok1.hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.kelompok1.hotel.model.BookingService;

public interface BookingServiceRepository extends JpaRepository<BookingService, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM `booking_services` WHERE `booking_id` = :bookingId AND `booking_service_id` NOT IN :id", nativeQuery = true)
    void deleteAllByIdNotIn(@Param("bookingId") Long bookingId, @Param("id") List<Long> id);
}
