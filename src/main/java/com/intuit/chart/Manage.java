package com.intuit.chart;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface Manage<T extends ManagedEmployee<? extends Employee>> {

    <K extends Employee> K instance();

    Set<T> getSubordinates();

    default void addSubordinate(T employee) {
        getSubordinates().add(employee);
        employee.setManager(instance());
    }

    default void addSubordinates(Set<T> employees) {
        getSubordinates().addAll(employees);
        employees.forEach(employee -> employee.setManager(instance()));
    }

    default void removeSubordinate(T employee) {
        getSubordinates().remove(employee);
    }

    default Optional<Employee> findByIdInSubordinates(UUID id) {
        return getSubordinates().parallelStream()
                .map(subordinate -> subordinate.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    default Optional<Manage> findManagerById(UUID id) {
        if (instance().getId().equals(id)) {
            return Optional.of(this);
        }
        return Optional.empty();
    }
}
