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
        return "rooms/index"; 
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("room", new Room());
        return "rooms/_form"; 
    }

    @PostMapping
    public String store(@Valid Room room, BindingResult result) {
        if (result.hasErrors()) {
            return "rooms/_form";
        }
        roomService.saveRoom(room);
        return "redirect:/rooms";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Room room = roomService.getRoomById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return ResponseEntity.ok(room);
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Room room = roomService.getRoomById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        model.addAttribute("room", room);
        return "rooms/_form"; 
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("room") Room room,
            BindingResult result) {
        if (result.hasErrors())
            return "rooms/_form";
        roomService.updateRoom(id, room);
        return "redirect:/rooms";
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return "redirect:/rooms";
    }
}
