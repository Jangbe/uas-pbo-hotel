package com.kelompok1.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kelompok1.hotel.enums.RoomMaintenanceStatus;
import com.kelompok1.hotel.enums.RoomStatus;
import com.kelompok1.hotel.model.Room;
import com.kelompok1.hotel.model.RoomMaintenance;
import com.kelompok1.hotel.service.RoomMaintenanceService;
import com.kelompok1.hotel.service.RoomService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/room-maintenance")
public class RoomMaintenanceController {

    @Autowired
    private RoomMaintenanceService roomMaintenanceService;
    @Autowired
    private RoomService roomService;

    @GetMapping
    public String main(Model model) {
        List<RoomMaintenance> rooms = roomMaintenanceService.getAllRoomMaintenances();
        model.addAttribute("rooms", rooms);
        return "room-maintenance/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        List<Room> rooms = roomService.getAllAvailableRooms();
        model.addAttribute("rooms", rooms);
        if (!model.containsAttribute("roomMaintenance"))
            model.addAttribute("roomMaintenance", new RoomMaintenance());
        return "room-maintenance/_form";
    }

    @PostMapping
    public String store(@Valid RoomMaintenance roomMaintenance, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.roomMaintenance", result);
            attributes.addFlashAttribute("roomMaintenance", roomMaintenance);
            return "redirect:/room-maintenance/create";
        }
        roomMaintenanceService.saveRoom(roomMaintenance);
        RoomMaintenanceStatus status = roomMaintenance.getStatus();
        if (status == RoomMaintenanceStatus.IN_PROGRESS)
            roomService.updateRoomStatus(roomMaintenance.getRoom().getRoomId(), RoomStatus.Maintenance);
        attributes.addFlashAttribute("message", "Berhasil menambah data pemeliharaan kamar");
        return "redirect:/room-maintenance";
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomMaintenance> getRoomById(@PathVariable Integer id) {
        RoomMaintenance roomMaintenance = roomMaintenanceService.getRoomById(id)
                .orElseThrow(() -> new RuntimeException("RoomMaintenance not found"));
        return ResponseEntity.ok(roomMaintenance);
    }

    @SuppressWarnings("null")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        if (!model.containsAttribute("roomMaintenance")) {
            RoomMaintenance roomMaintenance = roomMaintenanceService.getRoomById(id)
                    .orElseThrow(() -> new RuntimeException("RoomMaintenance not found"));
            model.addAttribute("roomMaintenance", roomMaintenance);
        }
        RoomMaintenance roomMaintenance = (RoomMaintenance) model.getAttribute("roomMaintenance");
        final List<Room> rooms = roomService.getAllAvailableRoomsExcept(roomMaintenance.getRoom().getRoomId());
        model.addAttribute("rooms", rooms);
        return "room-maintenance/_form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Integer id,
            @Valid @ModelAttribute("roomMaintenance") RoomMaintenance roomMaintenance,
            BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.roomMaintenance", result);
            attributes.addFlashAttribute("roomMaintenance", roomMaintenance);
            return "redirect:/room-maintenance/" + id + "/edit";
        }
        roomMaintenanceService.updateRoom(id, roomMaintenance);
        RoomMaintenanceStatus status = roomMaintenance.getStatus();
        if (status != RoomMaintenanceStatus.IN_PROGRESS)
            roomService.updateRoomStatus(roomMaintenance.getRoom().getRoomId(), RoomStatus.Available);
        attributes.addFlashAttribute("message", "Berhasil mengubah data pemeliharaan kamar");
        return "redirect:/room-maintenance";
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Integer id, RedirectAttributes attributes) {
        roomMaintenanceService.deleteRoom(id);
        attributes.addFlashAttribute("message", "Berhasil menghapus data pemeliharaan kamar");
        return "redirect:/room-maintenance";
    }
}
