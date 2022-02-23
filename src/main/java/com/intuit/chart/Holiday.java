package com.intuit.chart;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode
@Getter
public class Holiday {

    private final UUID id;
    @Setter private LocalDate startDate;
    @Setter private LocalDate endDate;

    public Holiday(LocalDate startDate, LocalDate endDate) {
        validateArguments(startDate, endDate);
        this.id = UUID.randomUUID();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Holiday(UUID id, LocalDate startDate, LocalDate endDate) {
        validateArguments(startDate, endDate);
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateArguments(LocalDate startDate, LocalDate endDate) {
        if (startDate == null
                || endDate == null
                || startDate.isAfter(endDate)
                || endDate.isBefore(LocalDate.now())) {
            throw new IllegalHolidayArgumentException();
        }
    }
}
