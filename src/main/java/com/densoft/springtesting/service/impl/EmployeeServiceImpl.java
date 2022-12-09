package com.densoft.springtesting.service.impl;

import com.densoft.springtesting.exception.ResourceNotFoundException;
import com.densoft.springtesting.model.Employee;
import com.densoft.springtesting.repository.EmployeeRepository;
import com.densoft.springtesting.service.EmployeeService;

import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {


    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent()) {
            throw new ResourceNotFoundException("Employee already exists with given email: " + employee.getEmail());
        }
        return employeeRepository.save(employee);
    }
}
