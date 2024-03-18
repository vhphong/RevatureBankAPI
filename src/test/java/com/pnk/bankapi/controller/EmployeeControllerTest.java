package com.pnk.bankapi.controller;

import com.pnk.bankapi.exception.BadRequestException;
import com.pnk.bankapi.model.Employee;
import com.pnk.bankapi.service.EmployeeService;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    Employee employee;

    String requestMapping = "/RevBankAPI/v2/employees";

    @BeforeEach
    void setUp() {
        employee = new Employee(1L, "John", 6);
    }

    @AfterEach
    void tearDown() {
        employee = null;
    }


    @Test
    void testContextLoads() {
        assertThat(employeeController).isNotNull();
        assertThat(employeeService).isNotNull();
    }


    @Test
    void testWelcomeEmployees() {
    }


    @Test
    void testSaveEmployee_ValidData_Success() throws Exception {
        // Mock the behavior of the employeeService.insertEmployee() method
        when(employeeService.insertEmployee(any(Employee.class))).thenReturn(employee);

        // Perform the POST request to the controller
        mockMvc.perform(post(requestMapping + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"employeeName\":\"John\",\"activeStatus\":6}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.employeeName").value("John"))
                .andExpect(jsonPath("$.activeStatus").value(6));

        // Verify that the employeeService.insertEmployee() method was called with the correct employee object
        verify(employeeService, times(1)).insertEmployee(any(Employee.class));
        verifyNoMoreInteractions(employeeService);


        // the test case below works fine
        /*
        // Arrange
        when(employeeService.insertEmployee(any(Employee.class))).thenReturn(employee);

        // Act
        ResponseEntity<Employee> response = employeeController.saveEmployee(employee);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employee, response.getBody());
        verify(employeeService, times(1)).insertEmployee(employee);*/
    }


    @Test
    void testSaveEmployee_NullData_BadRequestException() {
        // Arrange
        EmployeeService mockEmployeeService = mock(EmployeeService.class);
        EmployeeController employeeController = new EmployeeController(mockEmployeeService);

        // Act and Assert
        assertThrows(BadRequestException.class, () -> {
            employeeController.saveEmployee(null);
        });
        verify(employeeService, never()).insertEmployee(any(Employee.class));
    }


    @Test
    void testGetAllEmployees() throws Exception {
        List<Employee> employees = List.of(employee);
        when(employeeService.listAllEmployees()).thenReturn(employees);

        mockMvc.perform(MockMvcRequestBuilders.get(requestMapping)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(JSONArray.toJSONString(employees)));
    }


    @Test
    void testGetEmployeeByName() throws Exception {
        List<Employee> employees = List.of(employee);
        when(employeeService.listAllEmployeesByName("John")).thenReturn(employees);

        mockMvc.perform(MockMvcRequestBuilders.get(requestMapping + "/name/John")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(JSONArray.toJSONString(employees)));
    }


    @Test
    void testGetEmployeeByActiveStatus() throws Exception {
        List<Employee> employees = List.of(employee);
        when(employeeService.listAllEmployeesByActiveStatus(6)).thenReturn(employees);

        mockMvc.perform(MockMvcRequestBuilders.get(requestMapping + "/active_status/" + 6)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(JSONArray.toJSONString(employees)));
    }


    @Test
    void testActivateEmployeeProfile() throws Exception {
        employee.setActiveStatus(1);
        when(employeeService.enableEmployeeProfile(1L)).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.patch(requestMapping + "/activate/" + 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.employeeName").value("John"))
                .andExpect(jsonPath("$.activeStatus").value(1));
    }


    @Test
    void testDeactivateEmployeeProfile() throws Exception {
        employee.setActiveStatus(0);
        when(employeeService.disableEmployeeProfile(1L)).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.patch(requestMapping + "/deactivate/" + 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.employeeName").value("John"))
                .andExpect(jsonPath("$.activeStatus").value(0));
    }
}