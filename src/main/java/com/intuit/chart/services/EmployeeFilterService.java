package com.intuit.chart.services;

import com.intuit.chart.*;
import com.intuit.chart.exceptions.EmployeeNotFoundException;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class EmployeeFilterService {

    private final Ceo ceo;

    public EmployeeFilterService(Ceo ceo) {
        this.ceo = ceo;
    }

    public Ceo getCeo() {
        return ceo;
    }

    public Set<VicePresident> getVicePresidents() {
        return ceo.getSubordinates();
    }

    public Set<Director> getDirectors() {
        return getVicePresidents().parallelStream()
                .map(VicePresident::getSubordinates)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toSet());
    }

    public Set<Manager> getManagers() {
        return getDirectors().parallelStream()
                .map(Director::getSubordinates)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toSet());
    }

    public Set<SimpleEmployee> getSimpleEmployees() {
        return getManagers().parallelStream()
                .map(Manager::getSubordinates)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toSet());
    }

    public Employee findById(UUID employeeId) {
        return ceo.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    public Manage findManagerById(UUID employeeId) {
        return ceo.findManagerById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }
}
