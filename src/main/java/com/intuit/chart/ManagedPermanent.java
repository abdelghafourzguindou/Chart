package com.intuit.chart;

import java.util.UUID;

public class ManagedPermanent extends Permanent implements Managed, ChangeTeam {

    private Manage manager;

    public ManagedPermanent() {
        super(Role.Employee.PERMANENT);
    }

    protected ManagedPermanent(Role role) {
        super(role);
    }

    protected ManagedPermanent(UUID id, Role role) {
        super(id, role);
    }

    @Override
    public void setManager(Manage manager) {
        this.manager = manager;
    }

    @Override
    public Manage getManager() {
        return manager;
    }

    @Override
    public Employee getInfo() {
        return this;
    }

    @Override
    public void changeTeam(Manage manager) {
        this.manager.removeSubordinate(this);
        manager.addSubordinate(this);
        this.manager = manager;
    }
}
