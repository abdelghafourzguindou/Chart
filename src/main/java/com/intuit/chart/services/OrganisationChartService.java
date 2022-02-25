package com.intuit.chart.services;

import com.intuit.chart.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Is a service used to manage employees
 * Organisation char has one Ceo
 */
public class OrganisationChartService {

    private final EmployeeFilterService employeeFilterService;

    /**
     * Ceo of the created chart is uniq
     */
    private static Ceo ceo;

    public OrganisationChartService(Ceo ceo, EmployeeFilterService employeeFilterService) {
        this.employeeFilterService = employeeFilterService;
        OrganisationChartService.ceo = ceo;
    }

    /**
     * init Ceo, the uniq employee of that type in the company chart
     * @param firstName of ceo
     * @param lastName  of ceo
     * @param startDate of ceo
     */
    public void initCeoInfos(String firstName, String lastName, LocalDate startDate) {
        ceo.setFirstName(firstName);
        ceo.setLastName(lastName);
        ceo.setStartDate(startDate);
    }

    /**
     * Add a managed employee to subordinates of his manager
     * @param employee to add
     * @param managerId id of his manager
     */
    public void addEmployee(ManagedEmployee<?> employee, UUID managerId) {
        employeeFilterService.findManagerById(managerId).addSubordinate(employee);
    }

    /**
     * Move employee from team to a new team managed by manager how have managerId like id
     * @param employeeId of employee
     * @param managerId of new manager
     */
    public void moveTeam(UUID employeeId, UUID managerId) {
        Manage<?> newManager = this.employeeFilterService.findManagerById(managerId);

        this.employeeFilterService.getDirectors().parallelStream()
                .filter(director -> director.getId().equals(employeeId))
                .findFirst()
                .ifPresentOrElse(director -> director.moveTeam((VicePresident) newManager), () -> managerMoveTeam(employeeId, newManager));
    }

    /**
     * Specific move for Manager from his Director to a new one
     * @param employeeId manager id
     * @param newManager new director
     */
    private void managerMoveTeam(UUID employeeId, Manage<?> newManager) {
        this.employeeFilterService.getManagers().parallelStream()
                .filter(manager -> manager.getId().equals(employeeId))
                .findFirst()
                .ifPresentOrElse(director -> director.moveTeam((Director) newManager), () -> simpleEmployeeMoveTeam(employeeId, newManager));
    }

    /**
     * Specific move for SimpleEmployee from his Manager to a new one
     * @param employeeId simple employee id
     * @param newManager new manager
     */
    private void simpleEmployeeMoveTeam(UUID employeeId, Manage<?> newManager) {
        this.employeeFilterService.getSimpleEmployees().parallelStream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .ifPresent(director -> director.moveTeam((Manager) newManager));
    }
}
