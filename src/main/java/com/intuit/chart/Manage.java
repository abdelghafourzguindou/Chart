package com.intuit.chart;

import java.util.Set;

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
}
