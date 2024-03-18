package com.pnk.bankapi.service;

import com.pnk.bankapi.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee insertEmployee(Employee employee);

    List<Employee> listAllEmployees();

    List<Employee> listAllEmployeesByName(String emplNameInput);

    List<Employee> listAllEmployeesByActiveStatus(int statusInput);

    Employee enableEmployeeProfile(Long employeeIdInput);

    Employee disableEmployeeProfile(Long employeeIdInput);
}
