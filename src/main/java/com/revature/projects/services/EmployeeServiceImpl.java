package com.revature.projects.services;

import com.revature.projects.models.Employee;
import com.revature.projects.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        super();
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee insertEmployee(Employee employee) {
        employee.setEmployeeName(employee.getEmployeeName().toLowerCase());
        employee.setActiveStatus(employee.getActiveStatus());

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> listAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> listAllEmployeesByName(String emplNameInput) {
        return employeeRepository.findEmployeeByName(emplNameInput);
    }

    @Override
    public List<Employee> listAllEmployeesByActiveStatus(int statusInput) {
        return employeeRepository.findEmployeeByActiveStatus(statusInput);
    }
}
