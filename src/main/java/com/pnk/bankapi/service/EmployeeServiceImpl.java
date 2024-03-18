package com.pnk.bankapi.service;

import com.pnk.bankapi.exception.BadRequestException;
import com.pnk.bankapi.exception.ResourceNotFoundException;
import com.pnk.bankapi.model.Employee;
import com.pnk.bankapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;


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
        if (Objects.nonNull(employee)) {
            employee.setCreatedDate(new Date());
            return employeeRepository.save(employee);
        } else {
            throw new BadRequestException("Employee input is null!");
        }
    }


    @Override
    public List<Employee> listAllEmployees() {
        return employeeRepository.findAll();
    }


    @Override
    public List<Employee> listAllEmployeesByName(String emplNameInput) {
        return employeeRepository.findByEmployeeName(emplNameInput);
    }


    @Override
    public List<Employee> listAllEmployeesByActiveStatus(int statusInput) {
        return employeeRepository.findByActiveStatus(statusInput);
    }


    @Override
    public Employee enableEmployeeProfile(Long employeeIdInput) {
        Employee retrievedEmployee = employeeRepository.findById(employeeIdInput).orElseThrow(
                () -> new ResourceNotFoundException("Employee with Id: " + employeeIdInput + " is not found."));
        retrievedEmployee.setActiveStatus(1);
        employeeRepository.save(retrievedEmployee);

        return retrievedEmployee;
    }


    @Override
    public Employee disableEmployeeProfile(Long employeeIdInput) {
        Employee retrievedEmployee = employeeRepository.findById(employeeIdInput).orElseThrow(
                () -> new ResourceNotFoundException("Employee with Id: " + employeeIdInput + " is not found."));
        retrievedEmployee.setActiveStatus(0);
        employeeRepository.save(retrievedEmployee);

        return retrievedEmployee;
    }
}
