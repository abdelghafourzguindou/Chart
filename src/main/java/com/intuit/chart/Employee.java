package com.intuit.chart;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ToString
@EqualsAndHashCode
@Getter
public abstract class Employee {

    private final UUID id;
    @Getter private final Role role;
    @Setter private String firstName;
    @Setter private String lastName;
    @Setter private LocalDate startDate;
    @Setter private Set<Holiday> holidays;

    protected Employee(Role role) {
        this.id = UUID.randomUUID();
        this.role = role;
        this.holidays = new HashSet<>();
    }

    protected Employee(UUID id, Role role) {
        this.id = id;
        this.role = role;
        this.holidays = new HashSet<>();
    }
}
