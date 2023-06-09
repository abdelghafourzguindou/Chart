package com.intuit.chart;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * To be implemented by any Employee that can manage others
 * @param <T>: Type of employee managed
 */
public interface Manage<T extends ManagedEmployee<? extends Employee>> {

    /**
     * Return an instance of the class that implement Manage to get its attributes
     * @param <K> Type of employee
     * @return Employee
     */
    <K extends Employee> K instance();

    /**
     * Return subordinates managed
     * @return set of Employees
     */
    Set<T> getSubordinates();

    /**
     * Add a new managed employee in this manager subordinates
     * @param employee to add
     */
    default void addSubordinate(T employee) {
        // Add the new employee in manager subordinates
        getSubordinates().add(employee);
        // Set this manager as the added managed employee manager
        employee.setManager(instance());
    }

    /**
     * Add a set of managed employees in Manage subordinates
     * @param employees to be added
     */
    default void addSubordinates(Set<T> employees) {
        // Add all the new subordinates
        getSubordinates().addAll(employees);
        // Set this manager as the added managed employees manager
        employees.forEach(employee -> employee.setManager(instance()));
    }

    /**
     * Remove one subordinates from Manager set
     * @param employee to be removed
     */
    default void removeSubordinate(T employee) {
        getSubordinates().remove(employee);
    }

    /**
     * Search by employee id in subordinates
     * @param id of subordinate employee
     * @return Employee or Empty if no one match
     */
    default Optional<Employee> findByIdInSubordinates(UUID id) {
        return getSubordinates().parallelStream()
                .map(subordinate -> subordinate.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    /**
     * Search employee that can manage by id
     * @param id of manager
     * @return Manager that implement Manage interface or empty if no one match
     */
    default Optional<Manage> findManagerById(UUID id) {
        if (instance().getId().equals(id)) {
            return Optional.of(this);
        }
        return Optional.empty();
    }
}
