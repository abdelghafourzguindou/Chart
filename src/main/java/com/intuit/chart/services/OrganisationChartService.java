package com.intuit.chart.services;

import com.intuit.chart.Ceo;
import com.intuit.chart.ManagedEmployee;

import java.time.LocalDate;
import java.util.UUID;

public class OrganisationChartService {

    private static Ceo ceo;
    private final EmployeeFilterService employeeFilterService;

    public OrganisationChartService(Ceo ceo, EmployeeFilterService employeeFilterService) {
        this.employeeFilterService = employeeFilterService;
        OrganisationChartService.ceo = ceo;
    }

    public void initCeoInfos(String firstName, String lastName, LocalDate startDate) {
        ceo.setFirstName(firstName);
        ceo.setLastName(lastName);
        ceo.setStartDate(startDate);
    }

    public void addEmployee(ManagedEmployee<?> employee, UUID managerId) {
        employeeFilterService.findManagerById(managerId).addSubordinate(employee);
    }

    public void moveTeam(UUID employeeId, UUID managerId) {

    }
}
