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

import com.kelompok1.hotel.model.Service;
import com.kelompok1.hotel.service.ServiceService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/services")
public class ServiceController {
    Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @Autowired
    private ServiceService serviceService;

    @GetMapping
    public String main(Model model) {
        List<Service> services = serviceService.getAllService();
        model.addAttribute("services", services);
        return "service/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        if (!model.containsAttribute("service")) {
            model.addAttribute("service", new Service());
        }
        return "service/_form";
    }

    @PostMapping
    public String store(@Valid Service service, BindingResult result, RedirectAttributes attributes) {
        if (result.hasFieldErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.service", result);
            attributes.addFlashAttribute("service", service);
            return "redirect:/services/create";
        }
        serviceService.saveService(service);
        attributes.addFlashAttribute("message", "Berhasil menambah data layanan");
        return "redirect:/services";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable Long id) {
        Service service = serviceService.getServiceById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        return ResponseEntity.ok(service);
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        if (!model.containsAttribute("service")) {
            Service service = serviceService.getServiceById(id)
                    .orElseThrow(() -> new RuntimeException("Service not found"));
            model.addAttribute("service", service);
        }
        return "service/_form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("service") Service service,
            BindingResult result, RedirectAttributes attributes) {
        if (result.hasFieldErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.service", result);
            attributes.addFlashAttribute("service", service);
            return "redirect:/services/" + id + "/edit";
        }
        serviceService.updateService(id, service);
        attributes.addFlashAttribute("message", "Berhasil mengubah data layanan");
        return "redirect:/services";
    }

    @DeleteMapping("/{id}")
    public String deleteService(@PathVariable Long id, RedirectAttributes attributes) {
        serviceService.deleteService(id);
        attributes.addFlashAttribute("message", "Berhasil menghapus data layanan");
        return "redirect:/services";
    }
}
