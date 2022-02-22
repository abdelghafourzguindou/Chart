package com.intuit.chart;

public final class Contractor extends Employee implements Managed, ChangeTeam {

    private Manage manager;

    public Contractor() {
        super(Role.Employee.CONTRACTOR);
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
