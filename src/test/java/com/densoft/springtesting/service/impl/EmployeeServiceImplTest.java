package com.densoft.springtesting.service.impl;

import com.densoft.springtesting.exception.ResourceNotFoundException;
import com.densoft.springtesting.model.Employee;
import com.densoft.springtesting.repository.EmployeeRepository;
import com.densoft.springtesting.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setUp() {
//        employeeRepository = Mockito.mock(EmployeeRepository.class);
//        employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .id(1)
                .firstName("test")
                .lastName("user")
                .email("test@gmail.com")
                .build();
    }

    // junit test for save employee method
    @DisplayName("junit test for save employee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployee() {
        //given  - precondition or setup

        //stabbing methods in the mocked object
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        //when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeService.saveEmployee(employee);
        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // junit test for save Employee method throws exception
    @DisplayName("junit test for save Employee method throws exception ")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {
        //given  - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));
        //when - action or the behaviour that we are going to test
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        //then - verify the output
        verify(employeeRepository, never()).save(any(Employee.class));
    }


    // junit test for get all employees
    @DisplayName("junit test for get all employees")
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {
        //given  - precondition or setup
        Employee employeeTwo = Employee.builder().id(2).firstName("john").lastName("doe").email("john doe").build();
        List<Employee> employeeList = List.of(employee, employeeTwo);
        given(employeeRepository.findAll()).willReturn(employeeList);
        //when - action or the behaviour that we are going to test
        List<Employee> employees = employeeService.getAllEmployees();
        //then - verify the output
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(employeeList.size());
    }

    // junit test for get all employees negative scenario
    @DisplayName("junit test for get all employees negative scenario")
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
        //given  - precondition or setup
        List<Employee> employeeList = Collections.emptyList();
        given(employeeRepository.findAll()).willReturn(employeeList);
        //when - action or the behaviour that we are going to test
        List<Employee> employees = employeeService.getAllEmployees();
        //then - verify the output
        assertThat(employees).isEmpty();
        assertThat(employees.size()).isEqualTo(employeeList.size());
    }

    // junit test for get employee by id
    @DisplayName("junit test for get employee by id")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee() {
        //given  - precondition or setup
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
        //when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();
        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // junit test for update employee 
    @DisplayName("junit test for update employee ")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenUpdatedEmployee() {
        //given  - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        String email = "updatedemail@gmail.com";
        employee.setEmail(email);
        String newName = "new name";
        employee.setFirstName(newName);
        //when - action or the behaviour that we are going to test
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo(email);
        assertThat(updatedEmployee.getFirstName()).isEqualTo(newName);
    }


    // junit test for delete employee
    @DisplayName("junit test for delete employee ")
    @Test
    public void given_when_then() {
        //given  - precondition or setup
        long employeeId = 1l;
        willDoNothing().given(employeeRepository).deleteById(employeeId);
        //when - action or the behaviour that we are going to test
        employeeService.deleteEmployee(employeeId);
        //then - verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);

    }


}