package com.kelompok1.hotel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kelompok1.hotel.model.Employee;
import com.kelompok1.hotel.repository.EmployeeRepository;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder encoder;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Boolean uniqueEmail(String email, Long id) {
        return employeeRepository.checkUniqueEmail(email, id);
    }

    public Boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    public Employee saveEmployee(Employee employee) {
        employee.setPassword(encoder.encode(employee.getPassword()));
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee newEmployee) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setName(newEmployee.getName());
        employee.setEmail(newEmployee.getEmail());
        employee.setPhoneNumber(newEmployee.getPhoneNumber());
        employee.setPosition(newEmployee.getPosition());
        employee.setSalary(newEmployee.getSalary());
        employee.setHireDate(newEmployee.getHireDate());
        employee.setPassword(encoder.encode(newEmployee.getPassword()));
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
