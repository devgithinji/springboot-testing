package com.densoft.springtesting.repository;

import com.densoft.springtesting.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = Employee.builder()
                .firstName("test")
                .lastName("user")
                .email("test@gmail.com")
                .build();
    }

    // junit test for save employee operation
    @DisplayName("junit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployeeObject() {
        //given  - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("test")
//                .lastName("user")
//                .email("test@gmail.com")
//                .build();
        //when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeRepository.save(employee);
        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }


    // junit test for  get employee operation
    @DisplayName("junit test for get employee operation")
    @Test
    public void givenEmployeesList_whenFindAll_thenReturnEmployeesList() {
        //given  - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("test")
//                .lastName("user")
//                .email("test@gmail.com")
//                .build();
        Employee employeeTwo = Employee.builder()
                .firstName("john")
                .lastName("doe")
                .email("john@gmail.com")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employeeTwo);

        //when - action or the behaviour that we are going to test
        List<Employee> employees = employeeRepository.findAll();
        //then - verify the output
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(2);
    }

    // junit test for get employee by id
    @DisplayName("junit test for get employee by id")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
        //given  - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("test")
//                .lastName("user")
//                .email("test@gmail.com")
//                .build();
        Employee savedEmployee = employeeRepository.save(employee);
        //when - action or the behaviour that we are going to test
        Employee retrievedEmployee = employeeRepository.findById(savedEmployee.getId()).get();
        //then - verify the output
        assertThat(retrievedEmployee).isNotNull();
        assertThat(retrievedEmployee.getId()).isEqualTo(savedEmployee.getId());
    }


    // junit test for get employee by email operation
    @DisplayName("junit test for get employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
        //given  - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("test")
//                .lastName("user")
//                .email("test@gmail.com")
//                .build();
        Employee savedEmployee = employeeRepository.save(employee);
        //when - action or the behaviour that we are going to test
        Employee retrievedEmployee = employeeRepository.findByEmail(savedEmployee.getEmail()).get();
        //then - verify the output
        assertThat(retrievedEmployee).isNotNull();
        assertThat(retrievedEmployee.getEmail()).isEqualTo(savedEmployee.getEmail());
    }

    // junit test for update employee operation
    @DisplayName("junit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        //given  - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("test")
//                .lastName("user")
//                .email("test@gmail.com")
//                .build();
        Employee savedEmployee = employeeRepository.save(employee);
        //when - action or the behaviour that we are going to test
        String newEmail = "testuser@gmail.com";
        savedEmployee.setEmail(newEmail);
        Employee updatedEmployee = employeeRepository.save(savedEmployee);
        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo(newEmail);
    }

    // junit test for delete employee operation
    @DisplayName("junit test for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDeleteEmployee_thenRemoveEmployee() {
        //given  - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("test")
//                .lastName("user")
//                .email("test@gmail.com")
//                .build();
        Employee savedEmployee = employeeRepository.save(employee);
        //when - action or the behaviour that we are going to test
        employeeRepository.delete(savedEmployee);
        Optional<Employee> deletedEmployee = employeeRepository.findById(savedEmployee.getId());
        //then - verify the output
        assertThat(deletedEmployee).isEmpty();
    }

    // junit test for  custom query using JPQL with index parameters
    @DisplayName("junit test for custom query using JPQL with index parameters ")
    @Test
    public void givenFirstNameAndLastName_whenFindBYJPQL_thenReturnEmployeeObject() {
        //given  - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("test")
//                .lastName("user")
//                .email("test@gmail.com")
//                .build();
        Employee savedEmployee = employeeRepository.save(employee);
        String firstName = "test";
        String lastName = "user";
        //when - action or the behaviour that we are going to test
        Employee retrievedEmployee = employeeRepository.findByJPQL(firstName, lastName);
        //then - verify the output
        assertThat(retrievedEmployee).isNotNull();
        assertThat(retrievedEmployee.getFirstName()).isEqualTo(firstName);
        assertThat(retrievedEmployee.getLastName()).isEqualTo(lastName);
    }

    // junit test for  custom query using JPQL with named parameters
    @DisplayName("junit test for custom query using JPQL with named parameters ")
    @Test
    public void givenFirstNameAndLastName_whenFindBYJPQLWithNamedParams_thenReturnEmployeeObject() {
        //given  - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("test")
//                .lastName("user")
//                .email("test@gmail.com")
//                .build();
        Employee savedEmployee = employeeRepository.save(employee);
        String firstName = "test";
        String lastName = "user";
        //when - action or the behaviour that we are going to test
        Employee retrievedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);
        //then - verify the output
        assertThat(retrievedEmployee).isNotNull();
        assertThat(retrievedEmployee.getFirstName()).isEqualTo(firstName);
        assertThat(retrievedEmployee.getLastName()).isEqualTo(lastName);
    }

    // junit test for  custom query using Native SQL and Indexed Params
    @DisplayName("junit test for custom query using Native SQL and Indexed Params ")
    @Test
    public void givenFirstNameAndLastName_whenFindBYNativeSQLAndIndexParams_thenReturnEmployeeObject() {
        //given  - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("test")
//                .lastName("user")
//                .email("test@gmail.com")
//                .build();
        Employee savedEmployee = employeeRepository.save(employee);
        String firstName = "test";
        String lastName = "user";
        //when - action or the behaviour that we are going to test
        Employee retrievedEmployee = employeeRepository.findByNativeSQL(firstName, lastName);
        //then - verify the output
        assertThat(retrievedEmployee).isNotNull();
        assertThat(retrievedEmployee.getFirstName()).isEqualTo(firstName);
        assertThat(retrievedEmployee.getLastName()).isEqualTo(lastName);
    }

    // junit test for  custom query using Native SQL and Named Params
    @DisplayName("junit test for custom query using Native SQL and Named Params ")
    @Test
    public void givenFirstNameAndLastName_whenFindBYNativeSQLAndNamedParams_thenReturnEmployeeObject() {
        //given  - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("test")
//                .lastName("user")
//                .email("test@gmail.com")
//                .build();
        Employee savedEmployee = employeeRepository.save(employee);
        String firstName = "test";
        String lastName = "user";
        //when - action or the behaviour that we are going to test
        Employee retrievedEmployee = employeeRepository.findByNativeSQLNamedParams(firstName, lastName);
        //then - verify the output
        assertThat(retrievedEmployee).isNotNull();
        assertThat(retrievedEmployee.getFirstName()).isEqualTo(firstName);
        assertThat(retrievedEmployee.getLastName()).isEqualTo(lastName);
    }
}