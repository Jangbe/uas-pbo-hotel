package com.kelompok1.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kelompok1.hotel.model.Guest;
import com.kelompok1.hotel.service.GuestService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequestMapping("/guests")
public class GuestController {
    @Autowired
    private GuestService guestService;

    @GetMapping
    public String main(Model model) {
        List<Guest> guests = guestService.getAllGuest();
        model.addAttribute("guests", guests);
        return "guest/index";
    }

    @GetMapping("/all")
    public List<Guest> getAllGuest() {
        return guestService.getAllGuest();
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("guest", new Guest());
        return "guest/_form";
    }

    @PostMapping
    public String store(@ModelAttribute("guest") Guest guest) {
        guestService.saveGuest(guest);
        return "redirect:/guests";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Guest> getGuestById(@PathVariable Long id) {
        Guest guest = guestService.getGuestById(id).orElseThrow(() -> new RuntimeException("Guest not found"));
        return ResponseEntity.ok(guest);
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Guest guest = guestService.getGuestById(id).orElseThrow(() -> new RuntimeException("Guest not found"));
        model.addAttribute("guest", guest);
        return "guest/_form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("guest") Guest guest) {
        guestService.updateGuest(id, guest);
        return "redirect:/guests";
    }

    @DeleteMapping("/{id}")
    public String deleteGuest(@PathVariable Long id) {
        guestService.deleteGuest(id);
        return "redirect:/guests";
    }
}
