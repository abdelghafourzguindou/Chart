package com.intuit.chart;

import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;

public final class Director extends Manager {

    public Director() {
        super(Role.Management.DIRECTOR);
    }

    private Director(UUID id) {
        super(id, Role.Management.DIRECTOR);
    }

    @Override
    public void changeTeam(Manage manager) {
        Optional<Employee> managerToBeDirector = this.getSubordinates().parallelStream()
                .map(Managed::getInfo)
                .filter(employee -> employee.getRole().equals(Role.Management.MANAGER))
                .min(Comparator.comparing(Employee::getStartDate));

        if (managerToBeDirector.isPresent()) {
            Employee employee = managerToBeDirector.get();

            Director newDirector = new Director(employee.getId());
            newDirector.setFirstName(employee.getFirstName());
            newDirector.setLastName(employee.getLastName());
            newDirector.setStartDate(employee.getStartDate());
            newDirector.setHolidays(employee.getHolidays());
            newDirector.setManager(this.getManager());
            this.getManager().addSubordinate(newDirector);

            this.getSubordinates().remove(employee);

            this.getSubordinates().forEach(managed -> {
                newDirector.addSubordinate(managed);
                managed.setManager(newDirector);
            });

            this.cleanSubordinates();
        }

        this.getManager().removeSubordinate(this);
        manager.addSubordinate(this);
        this.setManager(manager);
    }
}
