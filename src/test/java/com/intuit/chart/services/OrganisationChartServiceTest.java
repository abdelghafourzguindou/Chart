package com.intuit.chart.services;

import com.intuit.chart.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class OrganisationChartServiceTest {

    private static final Ceo ceo = Ceo.getCeo();
    private static final EmployeeFilterService employeeFilterService = new EmployeeFilterService(ceo);
    private static final OrganisationChartService organisationChartService = new OrganisationChartService(ceo, employeeFilterService);

    @Test
    void should_init_ceo_infos() {
        final String firstName = "Ceo";
        final String lastName = "Ceo";
        final LocalDate startDate = LocalDate.now();

        organisationChartService.initCeoInfos(firstName, lastName, startDate);

        Assertions.assertThat(ceo.getFirstName()).isEqualTo(firstName);
        assertThat(ceo.getLastName()).isEqualTo(lastName);
        assertThat(ceo.getStartDate()).isEqualTo(startDate);
    }

    @Test
    void should_add_employee_successfully() {
        VicePresident vicePresident = new VicePresident();

        organisationChartService.addEmployee(vicePresident, ceo.getId());

        assertThat(ceo.getSubordinates()).contains(vicePresident);
        assertThat(vicePresident.getManager()).isEqualTo(ceo);
    }

    @Test
    void employee_should_move_team() {
        VicePresident vicePresident = new VicePresident();
        Director director = new Director();
        Manager manager1 = new Manager();
        Manager manager2 = new Manager();
        Permanent permanent1 = new Permanent();
        Permanent permanent2 = new Permanent();

        organisationChartService.addEmployee(vicePresident, ceo.getId());
        organisationChartService.addEmployee(director, vicePresident.getId());
        organisationChartService.addEmployee(manager1, director.getId());
        organisationChartService.addEmployee(manager2, director.getId());
        organisationChartService.addEmployee(permanent1, manager1.getId());
        organisationChartService.addEmployee(permanent2, manager2.getId());

        organisationChartService.moveTeam(permanent1.getId(), manager2.getId());

        assertThat(manager2.getSubordinates()).contains(permanent1);
        assertThat(manager1.getSubordinates()).doesNotContain(permanent1);
    }
}
