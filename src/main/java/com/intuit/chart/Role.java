package com.intuit.chart;

/**
 * Roles type inside company
 */
public interface Role {

    /**
     * Management Role
     */
    enum Management implements Role {
        CEO,
        VICE_PRESIDENT,
        DIRECTOR,
        MANAGER,
    }

    /**
     * Simple employee role
     */
    enum Employee implements Role {
        PERMANENT,
        CONTRACTOR
    }
}
