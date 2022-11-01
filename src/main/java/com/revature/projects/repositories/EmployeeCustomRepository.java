package com.revature.projects.repositories;

import com.revature.projects.models.Employee;

import java.util.List;

public interface EmployeeCustomRepository {

    List<Employee> findEmployeeByName(String nameInput);

    List<Employee> findEmployeeByActiveStatus(int statusInput);

//    Employee enableEmployeeActiveStatusByEmployeeId(long employeeIdInput);
}
