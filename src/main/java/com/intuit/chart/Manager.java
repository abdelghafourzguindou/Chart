package com.intuit.chart;

import java.util.*;

/**
 * Employee of type Manager is a managed employee by Director
 * Manager can manage simple employees
 * Manager can change team to another Director team
 */
public class Manager extends ManagedEmployee<Director> implements Manage<SimpleEmployee>, MoveTeam<Director> {

    /**
     * Manager subordinates are simple employees
     */
    private Set<SimpleEmployee> subordinates;

    public Manager() {
        super(Role.Management.MANAGER);
        this.subordinates = new HashSet<>();
    }

    /**
     * Used to created manager from promoted subordinate
     * @param id of promoted subordinate
     */
    private Manager(UUID id) {
        super(id, Role.Management.MANAGER);
        this.subordinates = new HashSet<>();
    }

    private Manager cloneFromSimpleEmployee(SimpleEmployee employee) {
        Manager manager = new Manager(employee.getId());
        manager.setFirstName(employee.getFirstName());
        manager.setLastName(employee.getLastName());
        manager.setStartDate(employee.getStartDate());
        manager.setHolidays(employee.getHolidays());
        return manager;
    }

    @Override
    public Manager instance() {
        return this;
    }

    @Override
    public Set<SimpleEmployee> getSubordinates() {
        return this.subordinates;
    }

    @Override
    public void moveTeam(Director newDirector) {
        promoteSeniorSubordinate();
        this.getManager().removeSubordinate(this);
        newDirector.addSubordinate(this);
    }

    /**
     * Promote the most senior simple employee to be the new Manager for this manage team
     */
    public void promoteSeniorSubordinate() {
        Optional<SimpleEmployee> employeeToBeManager = this.subordinates.parallelStream()
                // The promoted simple employee should be a permanent employee
                .filter(employee -> employee.getRole().equals(Role.Employee.PERMANENT))
                .min(Comparator.comparing(Employee::getStartDate));

        if (employeeToBeManager.isPresent()) {
            SimpleEmployee employee = employeeToBeManager.get();
            Manager newManager = cloneFromSimpleEmployee(employee);
            this.removeSubordinate(employee);
            newManager.addSubordinates(this.getSubordinates());
            this.getManager().addSubordinate(newManager);
            this.subordinates = new HashSet<>();
        }
    }

    @Override
    protected Optional<Employee> findById(UUID id) {
        Optional<Employee> employee = super.findById(id);
        if (employee.isPresent()) {
            return employee;
        }
        return findByIdInSubordinates(id);
    }
}
