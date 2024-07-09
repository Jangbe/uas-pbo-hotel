package com.kelompok1.hotel.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kelompok1.hotel.model.Employee;
import com.kelompok1.hotel.service.EmployeeService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService serviceEmployee;

    @GetMapping
    public String main(Model model) {
        List<Employee> employees = serviceEmployee.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employee/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        if (!model.containsAttribute("employee")) {
            model.addAttribute("employee", new Employee());
        }
        return "employee/_form";
    }

    @PostMapping
    public String store(@Valid Employee employee, BindingResult result, RedirectAttributes attributes) {
        if (serviceEmployee.existsByEmail(employee.getEmail())) {
            result.addError(new FieldError("email", "email", "Email sudah ada, gunakan email yang lain!"));
        }
        if (result.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.employee", result);
            attributes.addFlashAttribute("employee", employee);
            return "redirect:/employees/create";
        }
        serviceEmployee.saveEmployee(employee);
        attributes.addFlashAttribute("message", "Berhasil menambah data pegawai");
        return "redirect:/employees";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = serviceEmployee.getEmployeeById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        if (!model.containsAttribute("employee")) {
            Employee employee = serviceEmployee.getEmployeeById(id)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            model.addAttribute("employee", employee);
        }
        return "employee/_form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("employee") Employee employee,
            BindingResult result, RedirectAttributes attributes) {
        if (serviceEmployee.uniqueEmail(employee.getEmail(), id)) {
            result.addError(new FieldError("email", "email", "Email sudah ada, gunakan email yang lain!"));
        }
        if (result.hasFieldErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.employee", result);
            attributes.addFlashAttribute("employee", employee);
            return "redirect:/employees/" + id + "/edit";
        }
        serviceEmployee.updateEmployee(id, employee);
        attributes.addFlashAttribute("message", "Berhasil mengubah data pegawai");
        return "redirect:/employees";
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes attributes) {
        serviceEmployee.deleteEmployee(id);
        attributes.addFlashAttribute("message", "Berhasil menghapus data pegawai");
        return "redirect:/employees";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "register";
    }

    @PostMapping("/register")
    public String registerEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, RedirectAttributes attributes) {
        if (serviceEmployee.existsByEmail(employee.getEmail())) {
            result.addError(new FieldError("employee", "email", "Email sudah ada, gunakan email yang lain!"));
        }
        if (result.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.employee", result);
            attributes.addFlashAttribute("employee", employee);
            return "redirect:/employees/register";
        }
        serviceEmployee.saveEmployee(employee);
        attributes.addFlashAttribute("message", "Registrasi berhasil. Silakan login.");
        return "redirect:/login";
    }
}
