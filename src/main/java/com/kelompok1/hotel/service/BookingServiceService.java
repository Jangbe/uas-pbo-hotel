package com.kelompok1.hotel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kelompok1.hotel.model.BookingService;
import com.kelompok1.hotel.repository.BookingServiceRepository;

@Service
public class BookingServiceService {
    @Autowired
    private BookingServiceRepository bookingServiceRepository;

    public List<BookingService> getAllBookingService() {
        return bookingServiceRepository.findAll();
    }

    public Optional<BookingService> getBookingById(Long id) {
        return bookingServiceRepository.findById(id);
    }

    public BookingService saveBooking(BookingService service) {
        return bookingServiceRepository.save(service);
    }

    public BookingService updateBooking(Long id, BookingService serviceDetails) {
        BookingService bookingService = bookingServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        bookingService.setBooking(serviceDetails.getBooking());
        bookingService.setService(serviceDetails.getService());
        bookingService.setQuantity(serviceDetails.getQuantity());
        bookingService.setTotalPrice(serviceDetails.getTotalPrice());
        return bookingServiceRepository.save(bookingService);
    }

    public void deleteBooking(Long id) {
        bookingServiceRepository.deleteById(id);
    }

    public void deleteByIdNotIn(Long bookingId, List<Long> id) {
        bookingServiceRepository.deleteAllByIdNotIn(bookingId, id);
    }
}
