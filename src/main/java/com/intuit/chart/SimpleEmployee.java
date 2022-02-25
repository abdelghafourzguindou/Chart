package com.intuit.chart;

/**
 * Simple employee is an employee that should be managed by a Manager and can not manager others
 * Simple employee can change team to another manager team
 */
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
