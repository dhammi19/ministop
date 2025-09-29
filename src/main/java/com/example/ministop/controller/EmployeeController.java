package com.example.ministop.controller;

import com.example.ministop.entity.Employee;
import com.example.ministop.payload.request.EmployeeLoginRequest;
import com.example.ministop.payload.request.EmployeeRequest;
import com.example.ministop.payload.response.DataResponse;
import com.example.ministop.payload.response.EmployeeResponse;
import com.example.ministop.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("")
    public ResponseEntity<DataResponse> showEmployees() {
        List<EmployeeResponse> employees = employeeService.getEmployees();

        DataResponse dataResponse = new DataResponse();

        if(employees.isEmpty()) {
            dataResponse.setStatusCode(HttpStatus.OK.value());
            dataResponse.setSuccess(true);
            dataResponse.setDescription("There is no employee now");
            dataResponse.setData(employees);

            return ResponseEntity.status(HttpStatus.OK).body(dataResponse);
        } else {
            dataResponse.setStatusCode(HttpStatus.OK.value());
            dataResponse.setSuccess(true);
            dataResponse.setDescription("Get list of employees successfully");
            dataResponse.setData(employees);

            return ResponseEntity.status(HttpStatus.OK).body(dataResponse);
        }
    }

    @PostMapping("")
    public ResponseEntity<DataResponse> addEmployee(@RequestBody Employee employee) {
        DataResponse dataResponse = new DataResponse();

        boolean isAdded = employeeService.addEmployee(employee);

        if (isAdded) {
            dataResponse.setStatusCode(HttpStatus.CREATED.value());
            dataResponse.setSuccess(true);
            dataResponse.setDescription("Employee added successfully");
//            dataResponse.setData(employee);

            return ResponseEntity.status(HttpStatus.OK).body(dataResponse);
        } else {
            dataResponse.setStatusCode(HttpStatus.CONFLICT.value());
            dataResponse.setSuccess(false);
            dataResponse.setDescription("Invalid employee");
//            dataResponse.setData(employee);

            return ResponseEntity.status(HttpStatus.OK).body(dataResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse> showAnEmployee(@PathVariable String id) {
        List<EmployeeResponse> employeeList = employeeService.getAnEmployee(id);

        DataResponse response = new DataResponse();

        if (employeeList.isEmpty()) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setDescription("Couldn't find the employee");
            response.setData(null);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setDescription("The employee found");
            response.setData(employeeList);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PutMapping("")
    public ResponseEntity<DataResponse> updateEmployee(@RequestBody EmployeeRequest employeeRequest) {
        boolean isUpdated = employeeService.updateEmployee(employeeRequest);

        DataResponse response = new DataResponse();

        if (isUpdated) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setDescription("Employee updated");
            response.setData(employeeRequest);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setDescription("Update failed");
            response.setData(null);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse> deleteEmployee(@PathVariable String id) {
        boolean isDeleted = employeeService.deleteEmployee(id);

        DataResponse response = new DataResponse();

        if (isDeleted) {
            response.setStatusCode(HttpStatus.NO_CONTENT.value());
            response.setSuccess(true);
            response.setDescription("Employee deleted");
            response.setData(null);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setDescription("Deleting failed");
            response.setData(null);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<DataResponse> searchEmployees(
            @RequestParam String keyword
    ) {
        List<Employee> employees = employeeService.searchEmployees(keyword);

        DataResponse response = new DataResponse();

        response.setStatusCode(HttpStatus.OK.value());
        response.setSuccess(true);
        response.setDescription("searched successfully");
        response.setData(employees);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
