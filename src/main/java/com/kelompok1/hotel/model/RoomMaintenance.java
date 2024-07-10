package com.kelompok1.hotel.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.kelompok1.hotel.enums.RoomMaintenanceStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "room_maintenance")
public class RoomMaintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Integer maintenanceId;

    @ManyToOne()
    @JoinColumn(name = "room_id", nullable = false)
    @NotNull(message = "Ruang tidak boleh kosong")
    private Room room;

    @NotNull(message = "Deskripsi tidak boleh kosong")
    @Column()
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Tanggal maintenance tidak boleh kosong")
    @Column()
    private Date maintenanceDate;

    @NotNull(message = "Status tidak boleh kosong")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomMaintenanceStatus status;

}
