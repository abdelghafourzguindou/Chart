package com.intuit.chart.services;

import com.intuit.chart.Ceo;
import com.intuit.chart.Holiday;
import com.intuit.chart.exceptions.IllegalHolidayArgumentException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HolidayServiceTest {

    private static final Ceo ceo = Ceo.getCeo();
    private static final EmployeeFilterService employeeFilterService = new EmployeeFilterService(ceo);
    private static final HolidayService holidayService = new HolidayService(employeeFilterService);

    @Test
    void should_find_holiday_by_id() {
        final LocalDate startDate = LocalDate.now().plusMonths(1);
        final LocalDate endDate = startDate.plusMonths(2);

        holidayService.addHoliday(ceo.getId(), startDate, endDate);

        Optional<Holiday> holiday = ceo.getHolidays().parallelStream()
                .map(Holiday::getId)
                .findFirst()
                .flatMap(holidayId -> holidayService.findHolidayById(ceo.getId(), holidayId));

        assertThat(holiday).isPresent();
        assertThat(ceo.getHolidays()).contains(holiday.get());
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

        holidayService.addHoliday(ceo.getId(), startDate, endDate);

        Optional<UUID> holidayId = ceo.getHolidays().parallelStream()
                .map(Holiday::getId)
                .findFirst();

        assertThat(holidayId).isPresent();

        holidayService.removeHoliday(ceo.getId(), holidayId.get());
        Optional<Holiday> holiday = holidayService.findHolidayById(ceo.getId(), holidayId.get());
        assertThat(holiday).isNotPresent();
    }

    @Test
    void should_shift_holiday_successfully() {
        LocalDate startDate = LocalDate.now().plusMonths(1);
        LocalDate endDate = LocalDate.now().plusMonths(2);

        holidayService.addHoliday(ceo.getId(), startDate, endDate);

        Optional<UUID> holidayId = ceo.getHolidays().parallelStream()
                .map(Holiday::getId)
                .findFirst();

        assertThat(holidayId).isPresent();

        LocalDate shiftedStartDate = startDate.plusMonths(1);
        LocalDate shiftedEndDate = startDate.plusMonths(2);
        holidayService.shiftHoliday(ceo.getId(), holidayId.get(), shiftedStartDate, shiftedEndDate);

        Optional<Holiday> holiday = holidayService.findHolidayById(ceo.getId(), holidayId.get());

        assertThat(holiday).isPresent();

        assertThat(holiday.get().getStartDate()).isEqualTo(shiftedStartDate);
        assertThat(holiday.get().getEndDate()).isEqualTo(shiftedEndDate);
    }
}
