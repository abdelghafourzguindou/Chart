package com.intuit.chart.services;

import com.intuit.chart.ManagedEmployee;

import java.time.LocalDate;
import java.util.UUID;

public class OrganisationChartService {

    private final EmployeeFilterService employeeFilterService;

    public OrganisationChartService(EmployeeFilterService employeeFilterService) {
        this.employeeFilterService = employeeFilterService;
    }

    public void initCeoInfos(String firstName, String lastName, LocalDate startDate) {
        employeeFilterService.getCeo().setFirstName(firstName);
        employeeFilterService.getCeo().setLastName(lastName);
        employeeFilterService.getCeo().setStartDate(startDate);
    }

    public void addEmployee(ManagedEmployee<?> employee, UUID managerId) {
        employeeFilterService.findManagerById(managerId).addSubordinate(employee);
    }
}
