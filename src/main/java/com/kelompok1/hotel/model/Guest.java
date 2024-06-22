package com.kelompok1.hotel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "guests")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guestId;

    @NotBlank(message = "Nama depan tidak boleh kosong")
    @Column(nullable = false)
    private String firstName;
    
    @NotBlank(message = "Nama belakang tidak boleh kosong")
    @Column(nullable = false)
    private String lastName;
    
    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Email tidak valid")
    @Column(unique = true)
    private String email;
    
    @NotBlank(message = "No telepon boleh kosong")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Nomor hp harus diantara 10 - 15 digit")
    @Column()
    private String phoneNumber;
    
    @NotBlank(message = "Alamat boleh kosong")
    @Column()
    private String address;
    
    @NotBlank(message = "Tanggal lahir boleh kosong")
    @Column(name = "date_of_birth")
    private String dateOfBirth;
}
