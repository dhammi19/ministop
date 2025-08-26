package com.example.ministop.service;

import com.example.ministop.entity.EmployeeRole;
import com.example.ministop.repository.EmployeeRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeRoleImp implements EmployeeRoleService{
    @Autowired
    EmployeeRoleRepository employeeRoleRepository;

    @Override
    public List<EmployeeRole> getEmployeeRoles() {
        return employeeRoleRepository.findAll();
    }
}
