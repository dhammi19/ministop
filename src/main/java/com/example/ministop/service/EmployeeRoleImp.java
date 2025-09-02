package com.example.ministop.service;

import com.example.ministop.entity.EmployeeRole;
import com.example.ministop.payload.request.EmployeeRoleRequest;
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

    @Override
    public boolean addEmployeeRole(EmployeeRoleRequest request) {
        if (employeeRoleRepository.existsById(request.getRoleId())) {
            return false;
        }

        EmployeeRole role = new EmployeeRole();
        role.setRoleId(request.getRoleId());
        role.setRoleName(request.getRoleName());

        employeeRoleRepository.save(role);
        return true;
    }

    @Override
    public boolean updateEmployeeRole(String roleId, EmployeeRoleRequest request) {
        return employeeRoleRepository.findById(roleId).map(role -> {
            // chỉ update field cần thay đổi
            role.setRoleName(request.getRoleName());
            employeeRoleRepository.save(role);
            return true;
        }).orElse(false);
    }
}
