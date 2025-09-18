package com.example.ministop.service;

import com.example.ministop.entity.Employee;
import com.example.ministop.payload.request.EmployeeLoginRequest;
import com.example.ministop.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeImp implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public boolean isLoggedIn(EmployeeLoginRequest employeeLoginRequest) {
        List<Employee> employeeList = employeeRepository.getEmployeesByEmployeeIdAndPassword(
                employeeLoginRequest.getId(),
                employeeLoginRequest.getPassword());
        if (employeeList.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
