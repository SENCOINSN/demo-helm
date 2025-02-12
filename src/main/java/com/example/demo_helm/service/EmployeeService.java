package com.example.demo_helm.service;

import com.example.demo_helm.dto.EmployeeRequest;
import com.example.demo_helm.dto.EmployeeResponse;
import com.example.demo_helm.model.Employee;
import com.example.demo_helm.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeResponse save(EmployeeRequest employeeRequest) {
        return buildResponse(employeeRepository.save(buildEntity(employeeRequest)));
    }

    public List<EmployeeResponse> findAll() {
        return employeeRepository.findAll().stream().map(this::buildResponse).toList();
    }

    public EmployeeResponse findById(String matricule) {
        return buildResponse(employeeRepository.findById(matricule)
                .orElseThrow(() -> new EntityNotFoundException("Employee with matricule " + matricule + " not found")));
    }

    public EmployeeResponse update(String matricule, EmployeeRequest employeeRequest) {
        Employee employee = employeeRepository.findById(matricule)
                .orElseThrow(() -> new EntityNotFoundException("Employee with matricule " + matricule + " not found"));
        employee.setEmail(employeeRequest.email());
        employee.setFirstName(employeeRequest.firstName());
        employee.setLastName(employeeRequest.lastName());
        employee.setPhoneNumber(employeeRequest.phoneNumber());
        employee.setDepartment(employeeRequest.department());
        return buildResponse(employeeRepository.save(employee));
    }

    public void delete(String matricule) {
        employeeRepository.findById(matricule)
                .orElseThrow(() -> new EntityNotFoundException("Employee with matricule " + matricule + " not found"));
        employeeRepository.deleteById(matricule);
    }

    private EmployeeResponse buildResponse(Employee employee) {

        if (employee != null) {
            return new EmployeeResponse(
                    employee.getMatricule(),
                    employee.getEmail(),
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getPhoneNumber(),
                    employee.getDepartment().name()
            );
        }
        return null;
    }

    private Employee buildEntity(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        if (employeeRequest != null) {
            employee.setEmail(employeeRequest.email());
            employee.setFirstName(employeeRequest.firstName());
            employee.setLastName(employeeRequest.lastName());
            employee.setPhoneNumber(employeeRequest.phoneNumber());
            employee.setDepartment(employeeRequest.department());
        }
        return employee;
    }
    }

