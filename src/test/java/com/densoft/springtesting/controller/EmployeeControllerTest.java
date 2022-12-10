package com.densoft.springtesting.controller;

import com.densoft.springtesting.model.Employee;
import com.densoft.springtesting.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    // junit test for create employee
    @DisplayName("junit test for create employee ")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        //given  - precondition or setup
        Employee employee = Employee.builder()
                .firstName("test")
                .lastName("user")
                .email("test@gmail.com")
                .build();
        given(employeeService.saveEmployee(any(Employee.class))).willAnswer((invocation -> invocation.getArgument(0)));
        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee)));
        //then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    // junit test for get all employees REST API
    @DisplayName("junit test for get all employees REST API ")
    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
        //given  - precondition or setup
        List<Employee> employees = List.of(
                Employee.builder().firstName("test").lastName("user").email("test@gmail.com").build(),
                Employee.builder().firstName("john").lastName("doe").email("johndoe@gmail.com").build()
        );
        given(employeeService.getAllEmployees()).willReturn(employees);

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees"));
        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(employees.size())));
    }

    //positive scenario - valid employee id
    // junit test for GET employee by id REST API
    @DisplayName("junit test for GET employee by id REST API (positive scenario)")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        //given  - precondition or setup
        long employeeId = 1l;
        Employee employee = Employee.builder()
                .firstName("test")
                .lastName("user")
                .email("test@gmail.com")
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));
        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));
        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    //negative scenario - invalid employee id
    // junit test for GET employee by id REST API
    @DisplayName("junit test for GET employee by id REST API (negative scenario)")
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {
        //given  - precondition or setup
        long employeeId = 1l;
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));
        //then - verify the output
        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    // junit test for update employee rest API
    @DisplayName("junit test for update employee rest API")
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception {
        //given  - precondition or setup
        long employeeId = 1l;

        Employee employee = Employee.builder()
                .firstName("test")
                .lastName("user")
                .email("test@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("john")
                .lastName("doe")
                .email("johndoe@gmail.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));
        given(employeeService.updateEmployee(any(Employee.class))).willAnswer(invocation -> invocation.getArgument(0));
        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));
        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
    }

    // junit test for update employee rest API negative case
    @DisplayName("junit test for update employee rest API (negative case)")
    @Test
    public void givenInvalidEmployee_whenUpdateEmployee_thenReturnEmptyObject() throws Exception {
        //given  - precondition or setup
        long employeeId = 1l;

        Employee employee = Employee.builder()
                .firstName("test")
                .lastName("user")
                .email("test@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("john")
                .lastName("doe")
                .email("johndoe@gmail.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));
        //then - verify the output
        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    // junit test for delete employee
    @DisplayName("junit test for delete employee ")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
        //given  - precondition or setup
        long employeeId = 1l;
        willDoNothing().given(employeeService).deleteEmployee(employeeId);
        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employeeId));
        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk());
    }


}