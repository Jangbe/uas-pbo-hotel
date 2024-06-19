package com.kelompok1.hotel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

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
    @Column()
    private String phoneNumber;
    
    @NotBlank(message = "Alamat boleh kosong")
    @Column()
    private String address;
    
    @NotBlank(message = "Tanggal lahir boleh kosong")
    @Column(name = "date_of_birth")
    private String dateOfBirth;

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
