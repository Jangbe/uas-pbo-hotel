package com.kelompok1.hotel.controller;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kelompok1.hotel.enums.BookingStatus;
import com.kelompok1.hotel.enums.RoomStatus;
import com.kelompok1.hotel.model.Booking;
import com.kelompok1.hotel.service.BookingService;
import com.kelompok1.hotel.service.BookingServiceService;
import com.kelompok1.hotel.service.GuestService;
import com.kelompok1.hotel.service.RoomService;
import com.kelompok1.hotel.service.ServiceService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/bookings")
public class BookingController {
    Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookingServiceService bookingServiceService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private GuestService guestService;
    @Autowired
    private ServiceService serviceService;

    @GetMapping
    public String main(Model model) {
        List<Booking> bookings = bookingService.getAllBooking();
        model.addAttribute("bookings", bookings);
        return "booking/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("rooms", roomService.getAllAvailableRooms());
        model.addAttribute("guests", guestService.getAllGuest());
        model.addAttribute("services", serviceService.getAllService());
        if (!model.containsAttribute("booking")) {
            model.addAttribute("booking", new Booking());
        }
        return "booking/_form";
    }

    @PostMapping
    public String store(@Valid @ModelAttribute("booking") Booking booking, BindingResult result,
            RedirectAttributes attributes) {
        if (result.hasFieldErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.booking", result);
            attributes.addFlashAttribute("booking", booking);
            return "redirect:/bookings/create";
        }
        Double total = booking.getRoom().getPricePerNight();
        List<com.kelompok1.hotel.model.BookingService> bServices = new LinkedList<>();
        if (booking.getBookingServices() != null) {
            for (com.kelompok1.hotel.model.BookingService bs : booking.getBookingServices()) {
                com.kelompok1.hotel.model.Service s = bs.getService();
                if (s != null) {
                    bs.setBooking(booking);
                    bs.setTotalPrice(s.getPrice() * bs.getQuantity());
                    total += bs.getTotalPrice();
                    bServices.add(bs);
                }
            }
        }
        booking.setTotalAmount(total);
        booking.setBookingServices(bServices);
        bookingService.saveBooking(booking);
        roomService.updateRoomStatus(booking.getRoom().getRoomId(),
                booking.getStatus() == BookingStatus.CheckedOut ? RoomStatus.Available : RoomStatus.Occupied);
        attributes.addFlashAttribute("message", "Berhasil menambah data booking");
        return "redirect:/bookings";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Booking booking = bookingService.getBookingById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        model.addAttribute("rooms", roomService.getAllAvailableRoomsExcept(booking.getRoom().getRoomId()));
        model.addAttribute("guests", guestService.getAllGuest());
        model.addAttribute("services", serviceService.getAllService());
        if (!model.containsAttribute("booking")) {
            model.addAttribute("booking", booking);
        }
        return "booking/_form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("booking") Booking booking,
            BindingResult result, RedirectAttributes attributes) {
        if (result.hasFieldErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.booking", result);
            attributes.addFlashAttribute("booking", booking);
            return "redirect:/bookings/" + id + "/edit";
        }
        Double total = booking.getRoom().getPricePerNight();
        List<Long> ids = new LinkedList<Long>();
        if (booking.getBookingServices() != null) {
            for (com.kelompok1.hotel.model.BookingService bs : booking.getBookingServices()) {
                com.kelompok1.hotel.model.Service s = bs.getService();
                if (bs.getBookingServiceId() != null)
                    ids.add(bs.getBookingServiceId());
                if (s != null)
                    total += s.getPrice() * bs.getQuantity();
            }
        }
        if (ids.size() == 0)
            ids.add(Long.valueOf(0));

        booking.setTotalAmount(total);
        bookingService.updateBooking(id, booking);
        bookingServiceService.deleteByIdNotIn(id, ids);
        if (booking.getBookingServices() != null) {
            for (com.kelompok1.hotel.model.BookingService bs : booking.getBookingServices()) {
                com.kelompok1.hotel.model.Service s = bs.getService();
                if (s != null) {
                    bs.setTotalPrice(s.getPrice() * bs.getQuantity());
                    bs.setBooking(booking);
                    if (bs.getBookingServiceId() != null)
                        bookingServiceService.updateBooking(bs.getBookingServiceId(), bs);
                    else
                        bookingServiceService.saveBooking(bs);
                }
            }
        }
        roomService.updateRoomStatus(booking.getRoom().getRoomId(),
                booking.getStatus() == BookingStatus.CheckedOut ? RoomStatus.Available : RoomStatus.Occupied);
        attributes.addFlashAttribute("message", "Berhasil mengubah data booking");
        return "redirect:/bookings";
    }

    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable Long id, RedirectAttributes attributes) {
        bookingService.deleteBooking(id);
        attributes.addFlashAttribute("message", "Berhasil menghapus data booking");
        return "redirect:/bookings";
    }
}
