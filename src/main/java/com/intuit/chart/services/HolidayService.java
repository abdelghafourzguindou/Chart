package com.intuit.chart.services;

import com.intuit.chart.Holiday;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

/**
 * Is a holiday service to manage employee holidays
 */
public record HolidayService(EmployeeFilterService employeeFilterService) {

    public Optional<Holiday> findHolidayById(UUID employeeId, UUID holidayId) {
        return employeeFilterService.findById(employeeId)
                .getHolidays()
                .parallelStream()
                .filter(holiday -> holidayId.equals(holiday.getId()))
                .findFirst();
    }

    public void addHoliday(UUID employeeId, LocalDate startDate, LocalDate endDate) {
        employeeFilterService.findById(employeeId).addHoliday(new Holiday(startDate, endDate));
    }

    public void removeHoliday(UUID employeeId, UUID holidayId) {
        employeeFilterService.findById(employeeId).removeHoliday(holidayId);
    }

    public void shiftHoliday(UUID employeeId, UUID holidayId, LocalDate startDate, LocalDate endDate) {
        employeeFilterService.findById(employeeId).shiftHoliday(holidayId, startDate, endDate);
    }
}
