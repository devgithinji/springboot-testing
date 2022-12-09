package com.densoft.springtesting.service;

import com.densoft.springtesting.model.Employee;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {

    Employee saveEmployee(Employee employee);
}
