package com.intuit.chart.services;

import com.intuit.chart.*;
import com.intuit.chart.exceptions.EmployeeNotFoundException;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Is a service that used to filter in employees
 */
public record EmployeeFilterService(Ceo ceo) {

    /**
     * return the uniq company Ceo
     * @return Ceo
     */
    public Ceo getCeo() {
        return ceo;
    }

    /**
     * Return vice presidents employees in the company chart
     * @return Set<VicePresident>
     */
    public Set<VicePresident> getVicePresidents() {
        return ceo.getSubordinates();
    }

    /**
     * Return directors employees in the company chart
     * @return Set<Director>
     */
    public Set<Director> getDirectors() {
        return getVicePresidents().parallelStream()
                .map(VicePresident::getSubordinates)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toSet());
    }

    /**
     * Return all managers employees in the company chart
     * @return Set<Manager>
     */
    public Set<Manager> getManagers() {
        return getDirectors().parallelStream()
                .map(Director::getSubordinates)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toSet());
    }

    /**
     * Return all permanent & contractor employees in the company chart
     * @return Set<SimpleEmployee>
     */
    public Set<SimpleEmployee> getSimpleEmployees() {
        return getManagers().parallelStream()
                .map(Manager::getSubordinates)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toSet());
    }

    /**
     * Search employee by id
     * @param employeeId of the employee
     * @return Employee or throw EmployeeNotFoundException if no one match
     */
    public Employee findById(UUID employeeId) {
        return ceo.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    /**
     * Search employee that can manage other by id
     * @param employeeId of the employee that can manage
     * @return Manage or throw EmployeeNotFoundException if no one match
     */
    public Manage findManagerById(UUID employeeId) {
        return ceo.findManagerById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }
}
