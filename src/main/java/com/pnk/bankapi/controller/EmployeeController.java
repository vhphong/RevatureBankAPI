package com.pnk.bankapi.controller;

import com.pnk.bankapi.exception.BadRequestException;
import com.pnk.bankapi.model.Employee;
import com.pnk.bankapi.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@CrossOrigin(origins = {"http://trusted-domain.com", "https://trusted-domain.com"})
@CrossOrigin("*")   // *: all clients are able to call the CustomerController endpoint
@RestController
@RequestMapping("/RevBankAPI/v2/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    // to create employee
    /*
     * Body's JSON:
       {
            "employeeName": "John",
            "activeStatus": 0
        }

        should return

        {
            "employeeId": 2,
            "employeeName": "John",
            "activeStatus": 0,
            "createdDate": "2023-09-09T19:39:55.269+00:00",
            "lastUpdatedDate": "2023-09-09T19:39:55.269+00:00"
        }
    */
    // http://localhost:8081/RevBankAPI/v2/employees/create
    @PostMapping("/create")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        if (employee != null) {
            return new ResponseEntity<>(employeeService.insertEmployee(employee), HttpStatus.CREATED);
        } else {
            throw new BadRequestException("Request body does not contain customer data");
        }
    }


    // get all employees
    // http://localhost:8081/RevBankAPI/v2/employees
    @GetMapping("")
    public List<Employee> getAllEmployees() {
        return employeeService.listAllEmployees();
    }


    // get employees by employee name
    // http://localhost:8081/RevBankAPI/v2/employees/name/Phong
    @GetMapping("/name/{ename}")
    public List<Employee> getEmployeeByName(@PathVariable("ename") String emplName) {
        return employeeService.listAllEmployeesByName(emplName);
    }


    // get employees by employee active status
    // http://localhost:8081/RevBankAPI/v2/employees/active_status/1
    @GetMapping("/active_status/{status}")
    public List<Employee> getEmployeeByActiveStatus(@PathVariable("status") int emplActiveStatus) {
        return employeeService.listAllEmployeesByActiveStatus(emplActiveStatus);
    }


    // activate employee profile
    // http://localhost:8081/RevBankAPI/v2/employees/activate/2
    @PatchMapping("/activate/{id}")
    public ResponseEntity<Employee> activateEmployeeProfile(@PathVariable("id") Long emplId) {
        return new ResponseEntity<>(employeeService.enableEmployeeProfile(emplId), HttpStatus.OK);
    }


    // deactivate employee profile
    // http://localhost:8081/RevBankAPI/v2/employees/deactivate/2
    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<Employee> deactivateEmployeeProfile(@PathVariable("id") Long emplId) {
        return new ResponseEntity<>(employeeService.disableEmployeeProfile(emplId), HttpStatus.OK);
    }
}
