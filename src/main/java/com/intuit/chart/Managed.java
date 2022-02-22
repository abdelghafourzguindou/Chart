package com.intuit.chart;

public interface Managed {

    void setManager(Manage manager);

    Manage getManager();

    Employee getInfo();
}
