package com.intuit.chart;

public final class VicePresident extends Manager {

    public VicePresident() {
        super(Role.Management.VICE_PRESIDENT);
    }

    @Override
    public void changeTeam(Manage manager) {
        throw new UnsupportedOperationException();
    }
}
