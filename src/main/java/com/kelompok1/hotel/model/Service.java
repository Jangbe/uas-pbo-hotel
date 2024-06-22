package com.kelompok1.hotel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @NotBlank(message = "Nama layanan tidak boleh kosong")
    @Column(nullable = false)
    private String serviceName;
    
    @NotBlank(message = "Deskripsi tidak boleh kosong")
    @Column(nullable = false)
    private String description;
    
    @NotNull(message = "Harga tidak boleh kosong")
    @Column()
    private Double price;
}