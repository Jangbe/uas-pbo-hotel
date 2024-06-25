package com.kelompok1.hotel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kelompok1.hotel.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query(value = "select case when count(*) > 0 then 'true' else 'false' end from employees where email=?1 and employee_id!=?2 limit 1", nativeQuery = true)
    Boolean checkUniqueEmail(String email, Long employeeId);
}
