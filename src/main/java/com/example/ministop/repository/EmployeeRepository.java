package com.example.ministop.repository;

import com.example.ministop.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    List<Employee> getEmployeesByEmployeeIdAndPassword(String employeeId, String password);
    List<Employee> getEmployeesByEmployeeId(String employeeId);

    @Query(
            "select e from employee e " +
                    "where lower(e.employeeId) like lower(concat('%', :keyword, '%')) " +
                    "or lower(e.fullName) like lower(concat('%', :keyword, '%')) " +
                    "or cast(function('year', e.birthDate) as string) like concat('%', :keyword, '%') " +
                    "or lower(e.gender) like lower(concat('%', :keyword, '%')) " +
                    "or e.phoneNumber like concat('%', :keyword, '%') " +
                    "or lower(e.employeeRole.roleId) like lower(concat('%', :keyword, '%')) " +
                    "or lower(e.employeeRole.roleName) like lower(concat('%', :keyword, '%')) "
    )
    List<Employee> searchAllFields(@Param("keyword") String keyword);
}
