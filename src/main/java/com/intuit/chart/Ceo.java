package com.intuit.chart;

import java.util.HashSet;
import java.util.Set;

public final class Ceo extends Employee implements Manage<VicePresident> {

    private Set<VicePresident> subordinates;

    private static final Ceo ceo = new Ceo();

    private Ceo() {
        super(Role.Management.CEO);
        this.subordinates = new HashSet<>();
    }

    public static Ceo getCeo() {
        return ceo;
    }

    @Override
    public Ceo instance() {
        return this;
    }

    @Override
    public Set<VicePresident> getSubordinates() {
        return this.subordinates;
    }
}
