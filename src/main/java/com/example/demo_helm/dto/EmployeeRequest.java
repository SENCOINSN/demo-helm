package com.example.demo_helm.dto;

import com.example.demo_helm.model.Department;

public record EmployeeRequest(String lastName,
                              String firstName,
                              String email,
                              String phoneNumber,
                              Department department) {
}
