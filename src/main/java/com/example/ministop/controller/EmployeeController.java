package com.example.ministop.controller;

import com.example.ministop.entity.Employee;
import com.example.ministop.payload.request.EmployeeLoginRequest;
import com.example.ministop.payload.response.DataResponse;
import com.example.ministop.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<DataResponse> login(@RequestBody EmployeeLoginRequest employeeLoginRequest) {
        boolean isLoggedIn = employeeService.isLoggedIn(employeeLoginRequest);

        DataResponse dataResponse = new DataResponse();

        if (!isLoggedIn) {
            dataResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            dataResponse.setSuccess(false);
            dataResponse.setDescription("Login failed");
            dataResponse.setData(null);

            return ResponseEntity.status(HttpStatus.OK).body(dataResponse);
        } else {
            dataResponse.setStatusCode(HttpStatus.OK.value());
            dataResponse.setSuccess(true);
            dataResponse.setDescription("Login successful");
            dataResponse.setData(null);

            return ResponseEntity.status(HttpStatus.OK).body(dataResponse);
        }
    }
}
