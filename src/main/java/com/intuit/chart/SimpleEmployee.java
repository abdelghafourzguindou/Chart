package com.intuit.chart;

import java.util.UUID;

public abstract class SimpleEmployee extends ManagedEmployee<Manager> implements MoveTeam<Manager> {

    protected SimpleEmployee(Role role) {
        super(role);
    }

    protected SimpleEmployee(UUID id, Role role) {
        super(id, role);
    }

    @Override
    public void move(Manager newManager) {
        this.getManager().removeSubordinate(this);
        newManager.addSubordinate(this);
    }
}
