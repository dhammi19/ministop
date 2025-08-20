package com.example.ministop.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "employee")
public class Employee {
    @Id
    private String employeeId;

    @Column(name = "full_name")
    private String fullName;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "gender")
    private String gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private EmployeeRole employeeRole;
}