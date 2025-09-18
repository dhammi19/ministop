package com.example.ministop.repository;

import com.example.ministop.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    List<Employee> getEmployeesByEmployeeIdAndPassword(String employeeId, String password);
}
