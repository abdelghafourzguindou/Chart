package com.intuit.chart;

import java.util.*;

public class Manager extends ManagedEmployee<Director> implements Manage<SimpleEmployee>, MoveTeam<Director> {

    private Set<SimpleEmployee> subordinates;

    public Manager() {
        super(Role.Management.MANAGER);
        this.subordinates = new HashSet<>();
    }

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

    public void promoteSeniorSubordinate() {
        Optional<SimpleEmployee> employeeToBeManager = this.subordinates.parallelStream()
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
