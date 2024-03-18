package com.pnk.bankapi.repository;

import com.pnk.bankapi.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// @Repository   : no need @Repository annotation because JpaRepository already defined @Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByEmployeeName(String emplName);

    List<Employee> findByEmployeeNameContaining(String emplName);

    List<Employee> findByActiveStatus(int activeStatus);

}