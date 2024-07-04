package com.kelompok1.hotel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import com.kelompok1.hotel.enums.PaymentMethod;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long paymentId;

    @ManyToOne
    @NotNull(message = "Booking tidak boleh kosong")
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @NotNull(message = "Tanggal pembayaran tidak boleh kosong")
    @Column()
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date paymentDate;

    @NotNull(message = "Jumlah tidak boleh kosong")
    @Column()
    private BigDecimal amount;

    @NotNull(message = "Metode pembayaran tidak boleh kosong")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;
}
