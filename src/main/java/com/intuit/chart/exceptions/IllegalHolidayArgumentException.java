package com.intuit.chart.exceptions;

public class IllegalHolidayArgumentException extends IllegalArgumentException {

    public IllegalHolidayArgumentException() {
        super("Can not create holiday with those arguments");
    }
}
