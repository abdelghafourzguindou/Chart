package com.intuit.chart.services;

import com.intuit.chart.Ceo;
import com.intuit.chart.Employee;
import com.intuit.chart.exceptions.EmployeeNotFoundException;
import com.intuit.chart.Manage;

import java.util.UUID;

public class EmployeeFilterService {

    private final Ceo ceo;

    public EmployeeFilterService() {
        ceo = Ceo.getCeo();
    }

    public Ceo getCeo() {
        return ceo;
    }

    public Employee findById(UUID employeeId) {
        return ceo.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    public Manage findManagerById(UUID employeeId) {
        return ceo.findManagerById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }
}
