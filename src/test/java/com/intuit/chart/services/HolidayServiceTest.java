package com.intuit.chart.services;

import com.intuit.chart.Ceo;
import com.intuit.chart.Holiday;
import com.intuit.chart.exceptions.IllegalHolidayArgumentException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HolidayServiceTest {

    private static final EmployeeFilterService employeeFilterService = new EmployeeFilterService();
    private static final HolidayService holidayService = new HolidayService(employeeFilterService);
    private static final Ceo ceo = employeeFilterService.getCeo();

    @Test
    void should_find_holiday_by_id() {
        LocalDate startDate = LocalDate.now().plusMonths(1);
        LocalDate endDate = LocalDate.now().plusMonths(2);

        holidayService.addHoliday(ceo.getId(), startDate, endDate);

        ceo.getHolidays().parallelStream()
                .map(Holiday::getId)
                .findFirst()
                .flatMap(holidayId -> holidayService.findById(ceo.getId(), holidayId)).ifPresent((holiday) -> {
                    assertThat(holiday.getStartDate()).isEqualTo(startDate);
                    assertThat(holiday.getEndDate()).isEqualTo(endDate);
                });
    }

    @Test
    void should_add_holiday_successfully() {
        LocalDate startDate = LocalDate.now().plusMonths(1);
        LocalDate endDate = LocalDate.now().plusMonths(2);

        holidayService.addHoliday(ceo.getId(), startDate, endDate);

        assertThat(ceo.getHolidays()).isNotEmpty();
    }

    @Test
    void add_holiday_should_throw_IllegalHolidayArgument_if_arguments_not_match() {
        assertThatThrownBy(() -> new Holiday(null, LocalDate.now())).isInstanceOf(IllegalHolidayArgumentException.class);
        assertThatThrownBy(() -> new Holiday(LocalDate.now(), null)).isInstanceOf(IllegalHolidayArgumentException.class);
        assertThatThrownBy(() -> new Holiday(LocalDate.now().plusMonths(1), LocalDate.now())).isInstanceOf(IllegalHolidayArgumentException.class);
        assertThatThrownBy(() -> new Holiday(LocalDate.now().minusMonths(2), LocalDate.now().minusMonths(1))).isInstanceOf(IllegalHolidayArgumentException.class);
    }

    @Test
    void should_remove_holiday_successfully() {
        LocalDate startDate = LocalDate.now().plusMonths(1);
        LocalDate endDate = LocalDate.now().plusMonths(2);

        ceo.setHolidays(new HashSet<>());
        holidayService.addHoliday(ceo.getId(), startDate, endDate);

        ceo.getHolidays().parallelStream()
                .map(Holiday::getId)
                .findFirst()
                .ifPresent(holidayId -> {
                    holidayService.removeHoliday(ceo.getId(), holidayId);
                    assertThat(ceo.getHolidays()).isEmpty();
                });
    }

    @Test
    void should_shift_holiday_successfully() {
        LocalDate startDate = LocalDate.now().plusMonths(1);
        LocalDate endDate = LocalDate.now().plusMonths(2);

        holidayService.addHoliday(ceo.getId(), startDate, endDate);

        ceo.getHolidays().parallelStream()
                .map(Holiday::getId)
                .findFirst()
                .ifPresent(holidayId -> {
                    LocalDate shiftedStartDate = startDate.plusMonths(1);
                    LocalDate shiftedEndDate = startDate.plusMonths(2);
                    holidayService.shiftHoliday(ceo.getId(), holidayId, shiftedStartDate, shiftedEndDate);
                    holidayService.findById(ceo.getId(), holidayId).ifPresent((holiday) -> {
                        assertThat(holiday.getStartDate()).isEqualTo(shiftedStartDate);
                        assertThat(holiday.getEndDate()).isEqualTo(shiftedEndDate);
                    });
                });
    }
}
