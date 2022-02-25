package com.intuit.chart;

/**
 * Contractor is a simple employee
 * Contractor managed by a Manager
 * Contractor can not manager other employees
 */
public final class Contractor extends SimpleEmployee {

    public Contractor() {
        super(Role.Employee.CONTRACTOR);
    }
}
