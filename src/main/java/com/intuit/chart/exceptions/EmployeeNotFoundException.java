package com.intuit.chart.exceptions;

import java.util.UUID;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(UUID employeeId) {
        super("Employee with id: " + employeeId + "is not found");
    }
}
