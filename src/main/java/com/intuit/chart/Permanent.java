package com.intuit.chart;

/**
 * Permanent employee is a simple employee
 * Permanent should be managed by a Manager
 * Permanent can not manage other employees
 */
public class Permanent extends SimpleEmployee {

    public Permanent() {
        super(Role.Employee.PERMANENT);
    }
}
