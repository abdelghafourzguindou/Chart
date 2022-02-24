package com.intuit.chart;

import java.util.*;

public final class Director extends ManagedEmployee<VicePresident> implements Manage<Manager>, MoveTeam<VicePresident> {

    private Set<Manager> subordinates;

    public Director() {
        super(Role.Management.DIRECTOR);
        this.subordinates = new HashSet<>();
    }

    private Director(UUID id) {
        super(id, Role.Management.DIRECTOR);
        this.subordinates = new HashSet<>();
    }

    private Director cloneFromManager(Manager manager) {
        Director director = new Director(manager.getId());
        director.setFirstName(manager.getFirstName());
        director.setLastName(manager.getLastName());
        director.setStartDate(manager.getStartDate());
        director.setHolidays(manager.getHolidays());
        return director;
    }

    @Override
    public Director instance() {
        return this;
    }

    @Override
    public Set<Manager> getSubordinates() {
        return this.subordinates;
    }

    @Override
    public void moveTeam(VicePresident newVicePresident) {
        Optional<Manager> managerToBeDirector = this.subordinates.parallelStream().min(Comparator.comparing(Employee::getStartDate));

        if (managerToBeDirector.isPresent()) {
            Manager manager = managerToBeDirector.get();
            manager.promoteSeniorSubordinate();
            Director newDirector = cloneFromManager(manager);
            this.removeSubordinate(manager);
            newDirector.addSubordinates(this.getSubordinates());
            this.getManager().addSubordinate(newDirector);
            this.subordinates = new HashSet<>();
        }

        this.getManager().removeSubordinate(this);
        newVicePresident.addSubordinate(this);
    }

    @Override
    protected Optional<Employee> findById(UUID id) {
        Optional<Employee> employee = super.findById(id);
        if (employee.isPresent()) {
            return employee;
        }
        return findByIdInSubordinates(id);
    }

    @Override
    public Optional<Manage> findManagerById(UUID id) {
        Optional<Manage> manager = Manage.super.findManagerById(id);
        if (manager.isPresent()) {
            return manager;
        }
        return subordinates.parallelStream()
                .map(subordinate -> subordinate.findManagerById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }
}
