package com.intuit.chart;

import java.util.*;

/**
 * An employee of type Director is managed by an VicePresident.
 * He can Manage employee of type Manager
 * He can move team to another vice president team.
 */
public final class Director extends ManagedEmployee<VicePresident> implements Manage<Manager>, MoveTeam<VicePresident> {

    /**
     * Director Subordinates are Managers
     */
    private Set<Manager> subordinates;

    public Director() {
        super(Role.Management.DIRECTOR);
        this.subordinates = new HashSet<>();
    }

    /**
     * Used in private to clone promoted Manager that should be a Director
     * @param id of Manager
     */
    private Director(UUID id) {
        super(id, Role.Management.DIRECTOR);
        this.subordinates = new HashSet<>();
    }

    /**
     * Create a new Director from a promoted Manager
     * @param manager promoted
     * @return Director
     */
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

    /**
     * Director can change team to another vice president team
     * The most senior subordinate manager should be promoted to take the director place
     * @param newVicePresident new director manager
     */
    @Override
    public void moveTeam(VicePresident newVicePresident) {
        // Search of the most senior manager
        Optional<Manager> managerToBeDirector = this.subordinates.parallelStream().min(Comparator.comparing(Employee::getStartDate));

        // Promote the most senior manager to director and assign all director subordinates to him
        if (managerToBeDirector.isPresent()) {
            Manager manager = managerToBeDirector.get();
            manager.promoteSeniorSubordinate();
            Director newDirector = cloneFromManager(manager);
            // Remove the promoted manager from the director subordinates
            this.removeSubordinate(manager);
            newDirector.addSubordinates(this.getSubordinates());
            // Add the new director to the director manager subordinates
            this.getManager().addSubordinate(newDirector);
            // Empty the director subordinates
            this.subordinates = new HashSet<>();
        }

        // Remove director from his manager subordinates
        this.getManager().removeSubordinate(this);
        // Add director to the new manager subordinates
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
        // If it not this director change in the subordinates.
        return subordinates.parallelStream()
                .map(subordinate -> subordinate.findManagerById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }
}
