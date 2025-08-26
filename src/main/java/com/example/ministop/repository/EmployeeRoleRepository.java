package com.example.ministop.repository;

import com.example.ministop.entity.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, String> {
    List<EmployeeRole> findAll();
}
