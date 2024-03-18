package com.pnk.bankapi.service;

import com.pnk.bankapi.exception.BadRequestException;
import com.pnk.bankapi.exception.ResourceNotFoundException;
import com.pnk.bankapi.model.Employee;
import com.pnk.bankapi.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class EmployeeServiceImplTest {

    Employee employee1;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeService employeeService;
    @Mock
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        mock(Employee.class);
        mock(EmployeeRepository.class);

        autoCloseable = MockitoAnnotations.openMocks(this);   // not causing "this.employeeRepository" is null
        employeeService = new EmployeeServiceImpl(employeeRepository);  // not causing "this.employeeRepository" is null

        employee1 = new Employee(1L, "John Doe", 1);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testInsertEmployee() {
        // Success
        doReturn(employee1).when(employeeRepository).save(employee1);
//        when(employeeRepository.save(employee1)).thenReturn(employee1);       // works

        Employee result = employeeService.insertEmployee(employee1);

        assertEquals(employee1, result);
        verify(employeeRepository, times(1)).save(employee1);

        // test case: null employee, throw BadRequestException
        assertThrows(BadRequestException.class, () -> employeeService.insertEmployee(null));
    }


    @Test
    void testListAllEmployees() {
        doReturn(List.of(employee1)).when(employeeRepository).findAll();
//        when(employeeRepository.findAll()).thenReturn(List.of(employee1));    // works

        List<Employee> employeeList = employeeService.listAllEmployees();

        assertEquals(1, employeeList.size());
        assertEquals(employee1, employeeList.get(0));
    }


    @Test
    void testListAllEmployeesByName() {
        doReturn(List.of(employee1)).when(employeeRepository).findByEmployeeName(employee1.getEmployeeName());
//        when(employeeRepository.findByEmployeeName(employee1.getEmployeeName())).thenReturn(List.of(employee1));  // works

        List<Employee> employeeList = employeeService.listAllEmployeesByName(employee1.getEmployeeName());

        assertEquals(1, employeeList.size());
        assertEquals(employee1, employeeList.get(0));
    }


    @Test
    void testListAllEmployeesByActiveStatus() {
        doReturn(List.of(employee1)).when(employeeRepository).findByActiveStatus(employee1.getActiveStatus());
//        when(employeeRepository.findByActiveStatus(employee1.getActiveStatus())).thenReturn(List.of(employee1));  // works

        List<Employee> employeeList = employeeService.listAllEmployeesByActiveStatus(employee1.getActiveStatus());

        assertEquals(1, employeeList.size());
        assertEquals(employee1, employeeList.get(0));
    }


    @Test
    void testEnableEmployeeProfile() {
        // test case: Success
        doReturn(Optional.of(employee1)).when(employeeRepository).findById(1L);
//        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));     // works
        employee1.setActiveStatus(0);

        employeeService.enableEmployeeProfile(employee1.getEmployeeId());

        assertEquals(1, employee1.getActiveStatus());

        // test case: employee not found, then throw ResourceNotFoundException
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> employeeService.enableEmployeeProfile(99L));
    }


    @Test
    void testDisableEmployeeProfile() {
        // test case: Success
        doReturn(Optional.of(employee1)).when(employeeRepository).findById(1L);
//        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));     // works
        employee1.setActiveStatus(1);

        employeeService.disableEmployeeProfile(employee1.getEmployeeId());

        assertEquals(0, employee1.getActiveStatus());

        // test case: employee not found, then throw ResourceNotFoundException
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> employeeService.disableEmployeeProfile(99L));
    }
}
