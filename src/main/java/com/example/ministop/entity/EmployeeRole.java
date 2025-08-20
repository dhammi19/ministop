package com.example.ministop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "EmployeeRole")
public class EmployeeRole {
    @Id
    private String roleId;

    @Column(name = "role_name")
    private String roleName;
}
