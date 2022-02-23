package com.intuit.chart;

import com.intuit.chart.exceptions.IllegalHolidayArgumentException;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;

abstract class AbstractEmployeeTest<T extends Employee> {

    @Test
    void test_equals_and_hash_code_contracts() {
        EqualsVerifier.forClass(instance().getClass())
                .suppress(Warning.STRICT_INHERITANCE)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .verify();
    }

    @Test
    void test_getter_and_setter() {
        getBeanTester().testBean(instance().getClass());
    }

    @Test
    void employee_can_take_holiday() {
        Holiday holiday = new Holiday(LocalDate.now().minusMonths(1), LocalDate.now());
        instance().addHoliday(holiday);

        assertThat(instance().getHolidays()).contains(holiday);
    }

    @Test
    void employee_can_shift_holiday() {
        Holiday holiday = new Holiday(LocalDate.now().minusMonths(1), LocalDate.now());
        instance().addHoliday(holiday);
        LocalDate shiftedHolidayStartDate = LocalDate.now().plusMonths(1);
        LocalDate shiftedHolidayEndDate = LocalDate.now().plusMonths(2);
        instance().shiftHoliday(holiday.getId(), shiftedHolidayStartDate, shiftedHolidayEndDate);

        Optional<Holiday> shiftedHoliday = instance().getHolidays()
                .stream()
                .filter(holidayIterator -> holidayIterator.getId().equals(holiday.getId()))
                .findFirst();

        assertThat(shiftedHoliday).isPresent();
        assertThat(shiftedHoliday.get().getStartDate()).isEqualTo(shiftedHolidayStartDate);
        assertThat(shiftedHoliday.get().getEndDate()).isEqualTo(shiftedHolidayEndDate);
    }

    @Test
    void employee_can_decline_scheduled_holiday() {
        Holiday holiday1 = new Holiday(LocalDate.now().minusMonths(1), LocalDate.now());
        Holiday holiday2 = new Holiday(LocalDate.now().plusMonths(5), LocalDate.now().plusMonths(6));
        instance().addHoliday(holiday1);
        instance().addHoliday(holiday2);

        instance().removeHoliday(holiday1.getId());

        assertThat(instance().getHolidays()).doesNotContain(holiday1);
    }

    @Test
    void scheduled_holiday_with_illegal_arguments_should_throw_IllegalHolidayArgumentException() {
        assertThatThrownBy(() -> new Holiday(null, LocalDate.now())).isInstanceOf(IllegalHolidayArgumentException.class);
        assertThatThrownBy(() -> new Holiday(LocalDate.now(), null)).isInstanceOf(IllegalHolidayArgumentException.class);
        assertThatThrownBy(() -> new Holiday(LocalDate.now().plusMonths(1), LocalDate.now())).isInstanceOf(IllegalHolidayArgumentException.class);
        assertThatThrownBy(() -> new Holiday(LocalDate.now().minusMonths(2), LocalDate.now().minusMonths(1))).isInstanceOf(IllegalHolidayArgumentException.class);
    }

    @Test
    void should_find_employee_by_id() {
        Optional<Employee> filteredEmployee = instance().findById(instance().getId());
        assertThat(filteredEmployee).isPresent();
        assertSame(filteredEmployee.get(), instance());
    }

    protected abstract T instance();

    protected BeanTester getBeanTester() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection().addFactory(LocalDate.class, (Factory<LocalDate>) LocalDate::now);
        beanTester.getFactoryCollection().addFactory(Holiday.class, (Factory<Holiday>) () -> new Holiday(LocalDate.now(), LocalDate.now()));
        return beanTester;
    }
}
