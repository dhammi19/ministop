package com.example.ministop.service;

import com.example.ministop.entity.Employee;
import com.example.ministop.payload.request.EmployeeLoginRequest;
import com.example.ministop.payload.response.EmployeeResponse;
import com.example.ministop.repository.EmployeeRepository;
import com.example.ministop.repository.EmployeeRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeImp implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeRoleRepository employeeRoleRepository;

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

    @Override
    public List<EmployeeResponse> getEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponse> employeesResponseList = new ArrayList<>();

        for (Employee employee : employees) {
            employeesResponseList.add(
                    new EmployeeResponse(
                            employee.getEmployeeId(),
                            employee.getFullName(),
                            employee.getBirthDate(),
                            employee.getGender(),
                            employee.getPhoneNumber(),
                            employee.getEmployeeRole().getRoleName()
                    )
            );
        }

        return employeesResponseList;
    }

    @Override
    public boolean addEmployee(Employee employee) {
        if (employeeRepository.existsById(employee.getEmployeeId())) {
            return false;
        } else if (
                employee.getEmployeeRole().getRoleId() == null ||
                        !employeeRoleRepository.existsById(employee.getEmployeeRole().getRoleId())
        ) {
//            throw new IllegalArgumentException("Role không tồn tại trong hệ thống");
            return false;
        } else {
            employeeRepository.save(employee);
            return true;
        }
    }
}
