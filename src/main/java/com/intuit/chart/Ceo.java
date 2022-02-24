package com.intuit.chart;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public final class Ceo extends Employee implements Manage<VicePresident> {

    private Set<VicePresident> subordinates;

    private Ceo() {
        super(Role.Management.CEO);
        this.subordinates = new HashSet<>();
    }

    public static Ceo getCeo() {
        return new Ceo();
    }

    @Override
    public Ceo instance() {
        return this;
    }

    @Override
    public Set<VicePresident> getSubordinates() {
        return this.subordinates;
    }

    @Override
    public Optional<Employee> findById(UUID id) {
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
