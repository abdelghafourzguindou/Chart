package com.intuit.chart;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class OrganisationChartService {

    private final Ceo ceo;

    public OrganisationChartService() {
        this.ceo = Ceo.getCeo();
    }

    public void initCeoInfo(String firstName, String lastName, LocalDate startDate) {
        this.ceo.setFirstName(firstName);
        this.ceo.setLastName(lastName);
        this.ceo.setStartDate(startDate);
    }

    public void addEmployee(Managed employee, UUID managerId) {
        Optional<Employee> superior = this.ceo.getSubordinates().parallelStream()
                .map(Managed::getInfo)
                .filter(manager -> manager.getId().equals(managerId))
                .findFirst();

        // employee.setManager(superior);
    }
}
