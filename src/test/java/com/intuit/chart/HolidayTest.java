package com.intuit.chart;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class HolidayTest {

    @Test
    void should_throw_IllegalHolidayArgumentException_if_start_date_is_null() {
        Assertions.assertThatExceptionOfType(IllegalHolidayArgumentException.class)
                .isThrownBy(() -> new Holiday(null, LocalDate.now()));
    }

    @Test
    void should_throw_IllegalHolidayArgumentException_if_end_date_is_null() {
        Assertions.assertThatExceptionOfType(IllegalHolidayArgumentException.class)
                .isThrownBy(() -> new Holiday(LocalDate.now(), null));
    }

    @Test
    void should_throw_IllegalHolidayArgumentException_if_start_date_after_end_date() {
        Assertions.assertThatExceptionOfType(IllegalHolidayArgumentException.class)
                .isThrownBy(() -> new Holiday(LocalDate.now().plusMonths(1), LocalDate.now()));
    }

    @Test
    void should_throw_IllegalHolidayArgumentException_if_holiday_created_in_past() {
        Assertions.assertThatExceptionOfType(IllegalHolidayArgumentException.class)
                .isThrownBy(() -> new Holiday(LocalDate.now().minusMonths(2), LocalDate.now().minusMonths(1)));
    }
}
