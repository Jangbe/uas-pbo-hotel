package com.kelompok1.hotel.controller;

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

import com.kelompok1.hotel.model.Room;
import com.kelompok1.hotel.service.RoomService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    Logger logger = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private RoomService roomService;

    @GetMapping
    public String main(Model model) {
        List<Room> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "room/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        if (!model.containsAttribute("room"))
            model.addAttribute("room", new Room());
        return "room/_form";
    }

    @PostMapping
    public String store(@Valid Room room, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.room", result);
            attributes.addFlashAttribute("room", room);
            return "redirect:/rooms/create";
        }
        roomService.saveRoom(room);
        attributes.addFlashAttribute("message", "Berhasil menambah data kamar");
        return "redirect:/rooms";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Integer id) {
        Room room = roomService.getRoomById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return ResponseEntity.ok(room);
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        if (!model.containsAttribute("room")) {
            Room room = roomService.getRoomById(id)
                    .orElseThrow(() -> new RuntimeException("Room not found"));
            model.addAttribute("room", room);
        }
        return "room/_form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("room") Room room,
            BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.room", result);
            attributes.addFlashAttribute("room", room);
            return "redirect:/rooms/" + id + "/edit";
        }
        roomService.updateRoom(id, room);
        attributes.addFlashAttribute("message", "Berhasil mengubah data kamar");
        return "redirect:/rooms";
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Integer id, RedirectAttributes attributes) {
        roomService.deleteRoom(id);
        attributes.addFlashAttribute("message", "Berhasil menghapus data kamar");
        return "redirect:/rooms";
    }
}
