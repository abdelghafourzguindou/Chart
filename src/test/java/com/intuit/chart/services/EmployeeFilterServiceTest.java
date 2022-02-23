package com.intuit.chart.services;

import com.intuit.chart.*;
import com.intuit.chart.exceptions.EmployeeNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class EmployeeFilterServiceTest {

    private static final EmployeeFilterService employeeFilterService = new EmployeeFilterService();

    @Test
    void should_find_employee_by_id_successfully() {
        Permanent permanent = new Permanent();
        Manager manager = new Manager();
        Director director = new Director();
        VicePresident vicePresident = new VicePresident();

        Ceo.getCeo().addSubordinate(vicePresident);
        vicePresident.addSubordinate(director);
        director.addSubordinate(manager);
        manager.addSubordinate(permanent);

        Employee employee = employeeFilterService.findById(permanent.getId());

        assertThat(employee).isEqualTo(permanent);
    }

    @Test
    void find_employee_by_id_should_throw_EmployeeNotFoundException_when_id_not_match() {
        VicePresident vicePresident = new VicePresident();

        Ceo.getCeo().addSubordinate(vicePresident);

        assertThatExceptionOfType(EmployeeNotFoundException.class).isThrownBy(() -> employeeFilterService.findById(UUID.randomUUID()));
    }

    @Test
    void should_find_manager_by_id_successfully() {
        Permanent permanent = new Permanent();
        Manager manager = new Manager();
        Director director = new Director();
        VicePresident vicePresident = new VicePresident();

        Ceo.getCeo().addSubordinate(vicePresident);
        vicePresident.addSubordinate(director);
        director.addSubordinate(manager);
        manager.addSubordinate(permanent);

        Manage employee = employeeFilterService.findManagerById(manager.getId());

        assertThat(employee).isEqualTo(manager);
    }

    @Test
    void find_manager_by_id_should_throw_EmployeeNotFoundException_when_id_not_match() {
        VicePresident vicePresident = new VicePresident();

        Ceo.getCeo().addSubordinate(vicePresident);

        assertThatExceptionOfType(EmployeeNotFoundException.class).isThrownBy(() -> employeeFilterService.findManagerById(UUID.randomUUID()));
    }
}
