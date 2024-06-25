package com.kelompok1.hotel.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @NotBlank(message = "Nama harus diisi")
    @Column
    private String name;

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Email tidak valid")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "No HP tidak boleh kosong")
    @Column
    private String phoneNumber;

    @NotBlank(message = "Posisi tidak boleh kosong")
    @Column
    private String position;

    @NotNull(message = "Gaji tidak boleh kosong")
    @Column
    private Double salary;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Tanggal bergabung tidak boleh kosong")
    @Column
    private Date hireDate;

    @NotBlank(message = "Alamat tidak boleh kosong")
    @Column
    private String address;

    @NotBlank(message = "Password tidak boleh kosong")
    @Column
    private String password;
}
