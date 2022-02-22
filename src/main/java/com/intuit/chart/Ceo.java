package com.intuit.chart;

import java.util.HashSet;
import java.util.Set;

public final class Ceo extends Permanent implements Manage {

    private Set<Managed> subordinates;

    private static final Ceo ceo = new Ceo();

    private Ceo() {
        super(Role.Management.CEO);
        this.subordinates = new HashSet<>();
    }

    @Override
    public Set<Managed> getSubordinates() {
        return subordinates;
    }

    public static Ceo getCeo() {
        return ceo;
    }
}
