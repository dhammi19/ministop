package com.example.ministop.service;

import com.example.ministop.entity.EmployeeRole;
import com.example.ministop.payload.request.EmployeeRoleRequest;

import java.util.List;

public interface EmployeeRoleService {
    List<EmployeeRole> getEmployeeRoles();
    boolean addEmployeeRole(EmployeeRoleRequest request);
    boolean updateEmployeeRole(String roleId, EmployeeRoleRequest request);
}
