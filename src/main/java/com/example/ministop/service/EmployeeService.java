package com.example.ministop.service;

import com.example.ministop.entity.Employee;
import com.example.ministop.payload.request.EmployeeLoginRequest;
import com.example.ministop.payload.request.EmployeeRequest;
import com.example.ministop.payload.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    boolean isLoggedIn(EmployeeLoginRequest employeeLoginRequest);
    List<EmployeeResponse> getEmployees();
    boolean addEmployee(Employee employee);
    List<EmployeeResponse> getAnEmployee(String id);
    boolean updateEmployee(EmployeeRequest employeeRequest);
    boolean deleteEmployee(String id);
    List<Employee> searchEmployees(String keyword);
}
