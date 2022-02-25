package com.intuit.chart;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Abstract managed employee by manager of type T
 * @param <T> Manager type
 */
public abstract class ManagedEmployee<T extends Employee> extends Employee {

    @Getter
    @Setter
    private T manager;

    protected ManagedEmployee(Role role) {
        super(role);
    }

    protected ManagedEmployee(UUID id, Role role) {
        super(id, role);
    }
}
