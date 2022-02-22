package com.intuit.chart;

import java.util.Set;

public interface Manage {

    Set<Managed> getSubordinates();

    default Manage addSubordinate(Managed employee) {
        getSubordinates().add(employee);
        // employee.setManager(this);
        return this;
    }

    default void removeSubordinate(Managed employee) {
        getSubordinates().remove(employee);
    }
}
