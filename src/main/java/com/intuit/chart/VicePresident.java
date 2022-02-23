package com.intuit.chart;

import java.util.HashSet;
import java.util.Set;

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
}
