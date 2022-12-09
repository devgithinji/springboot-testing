package com.densoft.springtesting.service.impl;

import com.densoft.springtesting.model.Employee;
import com.densoft.springtesting.repository.EmployeeRepository;
import com.densoft.springtesting.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceImplTest {

    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    // junit test for save employee method
    @DisplayName("junit test for save employee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployee() {
        //given  - precondition or setup
        Employee employee = Employee.builder()
                .id(1)
                .firstName("test")
                .lastName("user")
                .email("test@gmail.com")
                .build();
        //stabbing methods in the mocked object
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        //when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeService.saveEmployee(employee);
        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }


}