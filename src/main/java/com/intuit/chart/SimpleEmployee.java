package com.intuit.chart;

public abstract class SimpleEmployee extends ManagedEmployee<Manager> implements MoveTeam<Manager> {

    protected SimpleEmployee(Role role) {
        super(role);
    }

    @Override
    public void moveTeam(Manager newManager) {
        this.getManager().removeSubordinate(this);
        newManager.addSubordinate(this);
    }
}
