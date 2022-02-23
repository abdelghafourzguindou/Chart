package com.intuit.chart;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public final class VicePresident extends ManagedEmployee<Ceo> implements Manage<Director> {

    private Set<Director> subordinates;

    public VicePresident() {
        super(Role.Management.VICE_PRESIDENT);
        subordinates = new HashSet<>();
    }

    @Override
    public VicePresident instance() {
        return this;
    }

    @Override
    public Set<Director> getSubordinates() {
        return this.subordinates;
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
