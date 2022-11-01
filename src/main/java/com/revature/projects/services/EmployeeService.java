package com.revature.projects.services;

import com.revature.projects.models.Employee;

import java.util.List;

public interface EmployeeService {

    Employee insertEmployee(Employee employee);

    List<Employee> listAllEmployees();

    List<Employee> listAllEmployeesByName(String emplNameInput);

    List<Employee> listAllEmployeesByActiveStatus(int statusInput);

    Employee enableEmployeeProfile(long employeeIdInput);

    Employee disableEmployeeProfile(long employeeIdInput);
}
