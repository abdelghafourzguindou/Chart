package com.intuit.chart;

import java.util.UUID;

public abstract class Permanent extends Employee {

    protected Permanent(Role role) {
        super(role);
    }

    protected Permanent(UUID id, Role role) {
        super(id, role);
    }
}
