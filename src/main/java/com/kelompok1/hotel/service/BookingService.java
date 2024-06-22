package com.kelompok1.hotel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kelompok1.hotel.model.Booking;
import com.kelompok1.hotel.repository.BookingRepository;

@Service
public class BookingService {
    @Autowired
    private BookingRepository serviceRepository;

    public List<Booking> getAllBooking() {
        return serviceRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return serviceRepository.findById(id);
    }

    public Booking saveBooking(Booking service) {
        return serviceRepository.save(service);
    }

    public Booking updateBooking(Long id, Booking serviceDetails) {
        Booking service = serviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        service.setGuest(serviceDetails.getGuest());
        service.setRoom(serviceDetails.getRoom());
        service.setCheckInDate(serviceDetails.getCheckInDate());
        service.setCheckOutDate(serviceDetails.getCheckOutDate());
        service.setTotalAmount(serviceDetails.getTotalAmount());
        service.setStatus(serviceDetails.getStatus());
        service.setCreatedAt(serviceDetails.getCreatedAt());
        return serviceRepository.save(service);
    }

    public void deleteBooking(Long id) {
        serviceRepository.deleteById(id);
    }
}
