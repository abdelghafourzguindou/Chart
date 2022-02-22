package com.intuit.chart;

public interface Role {

    enum Management implements Role {
        CEO,
        VICE_PRESIDENT,
        DIRECTOR,
        MANAGER,
    }

    enum Employee implements Role {
        PERMANENT,
        CONTRACTOR
    }
}
