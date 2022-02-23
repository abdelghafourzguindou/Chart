package com.intuit.chart.services;

import com.intuit.chart.Holiday;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class HolidayService {

    private final EmployeeFilterService employeeFilterService;

    public HolidayService(EmployeeFilterService employeeFilterService) {
        this.employeeFilterService = employeeFilterService;
    }

    public Optional<Holiday> findById(UUID employeeId, UUID holidayId) {
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
