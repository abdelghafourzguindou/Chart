package com.intuit.chart;

public class IllegalHolidayArgumentException extends IllegalArgumentException {

    public IllegalHolidayArgumentException() {
        super("Can not create holiday with those arguments");
    }
}
