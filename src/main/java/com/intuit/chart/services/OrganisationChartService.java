package com.intuit.chart.services;

import com.intuit.chart.*;

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
        Manage<?> newManager = this.employeeFilterService.findManagerById(managerId);

        this.employeeFilterService.getDirectors().parallelStream()
                .filter(director -> director.getId().equals(employeeId))
                .findFirst()
                .ifPresentOrElse(director -> director.moveTeam((VicePresident) newManager), () -> managerMoveTeam(employeeId, newManager));
    }

    private void managerMoveTeam(UUID employeeId, Manage<?> newManager) {
        this.employeeFilterService.getManagers().parallelStream()
                .filter(manager -> manager.getId().equals(employeeId))
                .findFirst()
                .ifPresentOrElse(director -> director.moveTeam((Director) newManager), () -> simpleEmployeeMoveTeam(employeeId, newManager));
    }

    private void simpleEmployeeMoveTeam(UUID employeeId, Manage<?> newManager) {
        this.employeeFilterService.getSimpleEmployees().parallelStream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .ifPresent(director -> director.moveTeam((Manager) newManager));
    }
}
