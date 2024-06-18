package com.kelompok1.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kelompok1.hotel.model.Service;
import com.kelompok1.hotel.service.ServiceService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequestMapping("/services")
public class ServiceController {
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
        model.addAttribute("service", new Service());
        return "service/_form";
    }

    @PostMapping
    public String store(@ModelAttribute("service") Service service) {
        serviceService.saveService(service);
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
        Service service = serviceService.getServiceById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        model.addAttribute("service", service);
        return "service/_form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("service") Service service) {
        serviceService.updateService(id, service);
        return "redirect:/services";
    }

    @DeleteMapping("/{id}")
    public String deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return "redirect:/services";
    }
}
