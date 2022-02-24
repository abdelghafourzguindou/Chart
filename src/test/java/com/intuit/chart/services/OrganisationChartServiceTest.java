package com.intuit.chart.services;

import com.intuit.chart.Ceo;
import com.intuit.chart.VicePresident;
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
}
