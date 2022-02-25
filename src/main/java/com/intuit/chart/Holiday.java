package com.intuit.chart;

import com.intuit.chart.exceptions.IllegalHolidayArgumentException;
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

    /**
     * Create holiday with a random id from start day to end date
     * @param startDate of holiday
     * @param endDate of holiday
     */
    public Holiday(LocalDate startDate, LocalDate endDate) {
        validateArguments(startDate, endDate);
        this.id = UUID.randomUUID();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Create holiday with a specific id from start day to end date
     * @param id of holiday
     * @param startDate of holiday
     * @param endDate of holiday
     */
    public Holiday(UUID id, LocalDate startDate, LocalDate endDate) {
        validateArguments(startDate, endDate);
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Validate holiday arguments
     * Holiday start date should be before end date
     * Holiday start date & end date should not be null
     * Holiday end date should be in the future
     * @param startDate of holiday
     * @param endDate of holiday
     */
    private void validateArguments(LocalDate startDate, LocalDate endDate) {
        if (startDate == null
                || endDate == null
                || startDate.isAfter(endDate)
                || endDate.isBefore(LocalDate.now())) {
            throw new IllegalHolidayArgumentException();
        }
    }
}
