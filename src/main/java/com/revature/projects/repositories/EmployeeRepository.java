package com.revature.projects.repositories;

import com.revature.projects.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

// @Repository   : no need @Repository annotation because JpaRepository already defined @Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeCustomRepository {
}
