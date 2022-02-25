package com.intuit.chart;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Abstract employee with the necessaries attributes
 * Employee can take holidays
 * Employee always have a role
 */
@ToString
@EqualsAndHashCode
@Getter
public abstract class Employee {

    private final UUID id;
    private final Role role;
    @Setter private String firstName;
    @Setter private String lastName;
    @Setter private LocalDate startDate;
    @Setter private Set<Holiday> holidays;

    /**
     * Create Employee with specific role
     * @param role of employee
     */
    protected Employee(Role role) {
        this.id = UUID.randomUUID();
        this.role = role;
        this.holidays = new HashSet<>();
    }

    /**
     * Create Employee with id and role
     * @param id of employee
     * @param role of employee
     */
    protected Employee(UUID id, Role role) {
        this.id = id;
        this.role = role;
        this.holidays = new HashSet<>();
    }

    public void addHoliday(Holiday holiday) {
        this.holidays.add(holiday);
    }

    public void removeHoliday(UUID holidayId) {
        this.holidays.stream()
                .filter(holiday -> holiday.getId().equals(holidayId))
                .findFirst()
                .ifPresent(holiday -> this.holidays.remove(holiday));
    }

    public void shiftHoliday(UUID holidayId, LocalDate startDate, LocalDate endDate) {
        this.removeHoliday(holidayId);
        this.addHoliday(new Holiday(holidayId, startDate, endDate));
    }

    protected Optional<Employee> findById(UUID id) {
        return (id.equals(this.id)) ? Optional.of(this): Optional.empty();
    }
}
