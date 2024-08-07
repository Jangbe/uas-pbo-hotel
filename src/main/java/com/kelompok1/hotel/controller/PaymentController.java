package com.kelompok1.hotel.controller;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kelompok1.hotel.model.Booking;
import com.kelompok1.hotel.model.Payment;
import com.kelompok1.hotel.service.BookingService;
import com.kelompok1.hotel.service.PaymentService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/payments")
public class PaymentController {
    Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public String main(Model model) {
        List<Payment> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "booking/payment";
    }

    @GetMapping("/booking/{id}/payment")
    public String create(@PathVariable("id") Long bookingId, Model model) {
        Payment payment = new Payment();
        Booking booking = bookingService.getBookingById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        payment.setBooking(booking);
        List<Payment> payments = paymentService.getPaymentsByBookingId(bookingId);
        
        double totalPayments = payments.stream()
                                    .map(Payment::getAmount)
                                    .mapToDouble(BigDecimal::doubleValue)
                                    .sum();
        boolean isPaidOff = totalPayments >= booking.getTotalAmount().doubleValue();

        model.addAttribute("payment", payment);
        model.addAttribute("payments", payments);
        model.addAttribute("totalAmount", booking.getTotalAmount());
        model.addAttribute("isPaidOff", isPaidOff);
        return "booking/payment";
    }

    @PostMapping
    public String store(@Valid Payment payment, BindingResult result, RedirectAttributes attributes) {
        Long bookingId = payment.getBooking().getBookingId();
        if (result.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.payment", result);
            attributes.addFlashAttribute("payment", payment);
            return "redirect:/payments/booking/" + bookingId + "/payment";
        }
        paymentService.savePayment(payment);
        attributes.addFlashAttribute("message", "Berhasil menambah data pembayaran");
        return "redirect:/payments/booking/" + bookingId + "/payment";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/booking/{bookingId}/payment/{paymentId}/edit")
    public String edit(@PathVariable("bookingId") Long bookingId, @PathVariable("paymentId") Long paymentId, Model model) {
        Payment payment = paymentService.getPaymentById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        List<Payment> payments = paymentService.getPaymentsByBookingId(bookingId);
    
        // Hitung total pembayaran
        double totalPayments = payments.stream()
                                       .map(Payment::getAmount)
                                       .mapToDouble(BigDecimal::doubleValue)
                                       .sum();
        boolean isPaidOff = totalPayments >= payment.getBooking().getTotalAmount().doubleValue();
    
        model.addAttribute("payment", payment);
        model.addAttribute("payments", payments);
        model.addAttribute("totalAmount", payment.getBooking().getTotalAmount());
        model.addAttribute("isPaidOff", isPaidOff);
        return "booking/payment";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("payment") Payment payment,
            BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.payment", result);
            attributes.addFlashAttribute("payment", payment);
            return "redirect:/payments/" + id + "/edit";
        }
        paymentService.updatePayment(id, payment);
        Long bookingId = payment.getBooking().getBookingId();
        attributes.addFlashAttribute("message", "Berhasil mengubah data pembayaran");
        return "redirect:/payments/booking/" + bookingId + "/payment";
    }


    @DeleteMapping("/{id}")
    public String deletePayment(@PathVariable Long id, RedirectAttributes attributes) {
        Payment payment = paymentService.getPaymentById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        Long bookingId = payment.getBooking().getBookingId();
        paymentService.deletePayment(id);
        attributes.addFlashAttribute("message", "Berhasil menghapus data pembayaran");
        return "redirect:/payments/booking/" + bookingId + "/payment";
    }
}
