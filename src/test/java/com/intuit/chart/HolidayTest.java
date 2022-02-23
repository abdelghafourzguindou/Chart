package com.intuit.chart;

import com.intuit.chart.exceptions.IllegalHolidayArgumentException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HolidayTest {

    @Test
    void should_throw_IllegalHolidayArgumentException_if_start_date_is_null() {
        assertThatThrownBy(() -> new Holiday(null, LocalDate.now())).isInstanceOf(IllegalHolidayArgumentException.class);
    }

    @Test
    void should_throw_IllegalHolidayArgumentException_if_end_date_is_null() {
        assertThatThrownBy(() -> new Holiday(LocalDate.now(), null)).isInstanceOf(IllegalHolidayArgumentException.class);
    }

    @Test
    void should_throw_IllegalHolidayArgumentException_if_start_date_after_end_date() {
        assertThatThrownBy(() -> new Holiday(LocalDate.now().plusMonths(1), LocalDate.now())).isInstanceOf(IllegalHolidayArgumentException.class);
    }

    @Test
    void should_throw_IllegalHolidayArgumentException_if_holiday_created_in_past() {
        assertThatThrownBy(() -> new Holiday(LocalDate.now().minusMonths(2), LocalDate.now().minusMonths(1))).isInstanceOf(IllegalHolidayArgumentException.class);
    }
}
