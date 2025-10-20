package com.example.ministop.controller;

import com.example.ministop.entity.Customer;
import com.example.ministop.payload.response.DataResponse;
import com.example.ministop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("")
    public ResponseEntity<DataResponse> showCustomers() {
        List<Customer> customerList = customerService.getCustomers();

        DataResponse response = new DataResponse();
        response.setStatusCode(HttpStatus.OK.value());
        response.setSuccess(true);
        response.setDescription("Get employee list successfully");
        response.setData(customerList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
