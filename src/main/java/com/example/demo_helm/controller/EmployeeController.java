package com.example.demo_helm.controller;

import com.example.demo_helm.constants.ApiPath;
import com.example.demo_helm.dto.EmployeeRequest;
import com.example.demo_helm.dto.EmployeeResponse;
import com.example.demo_helm.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPath.EMPLOYEES)
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        return  new ResponseEntity<>(employeeService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> create(@RequestBody EmployeeRequest request){
        return new ResponseEntity<>(employeeService.save(request), HttpStatus.CREATED);
    }

    @PutMapping("{matricule}")
    public ResponseEntity<EmployeeResponse> update(@PathVariable String matricule, @RequestBody EmployeeRequest request){
        return new ResponseEntity<>(employeeService.update(matricule, request), HttpStatus.OK);
    }

    @DeleteMapping("{matricule}")
    public ResponseEntity<Void> delete(@PathVariable String matricule){
        employeeService.delete(matricule);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{matricule}")
    public ResponseEntity<EmployeeResponse> findById(@PathVariable String matricule){
        return new ResponseEntity<>(employeeService.findById(matricule), HttpStatus.OK);
    }
}
