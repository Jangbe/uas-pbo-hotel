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
        model.addAttribute("service", new Service());
        return "service/_form";
    }

    @PostMapping
    public String store(@Valid Service service, BindingResult result) {
        if (result.hasErrors()) {
            return "service/_form";
        }
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
    public String update(@PathVariable Long id, @Valid @ModelAttribute("service") Service service,
            BindingResult result) {
        if (result.hasErrors())
            return "service/_form";
        serviceService.updateService(id, service);
        return "redirect:/services";
    }

    @DeleteMapping("/{id}")
    public String deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return "redirect:/services";
    }
}
