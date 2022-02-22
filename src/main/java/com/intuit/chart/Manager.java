package com.intuit.chart;

import java.util.*;

public class Manager extends ManagedPermanent implements Manage {

    private Set<Managed> subordinates;

    public Manager() {
        super(Role.Management.MANAGER);
        this.subordinates = new HashSet<>();
    }

    protected Manager(Role role) {
        super(role);
        this.subordinates = new HashSet<>();
    }

    protected Manager(UUID id, Role role) {
        super(id, role);
        this.subordinates = new HashSet<>();
    }

    private Manager(UUID id) {
        super(id, Role.Management.MANAGER);
        this.subordinates = new HashSet<>();
    }

    @Override
    public Set<Managed> getSubordinates() {
        return subordinates;
    }


    @Override
    public void changeTeam(Manage manager) {
        Optional<Employee> employeeToBeManager = this.subordinates.parallelStream()
                .map(Managed::getInfo)
                .filter(employee -> employee.getRole().equals(Role.Employee.PERMANENT))
                .max(Comparator.comparing(Employee::getStartDate));

        if (employeeToBeManager.isPresent()) {
            Employee employee = employeeToBeManager.get();

            Manager newManager = new Manager(employee.getId());
            newManager.setFirstName(employee.getFirstName());
            newManager.setLastName(employee.getLastName());
            newManager.setStartDate(employee.getStartDate());
            newManager.setHolidays(employee.getHolidays());
            newManager.setManager(this.getManager());
            this.getManager().addSubordinate(newManager);

            this.subordinates.remove(employee);

            this.subordinates.forEach(managed -> {
                managed.setManager(newManager);
                newManager.addSubordinate(managed);
            });

            cleanSubordinates();
        }

        super.changeTeam(manager);
    }

    protected void cleanSubordinates() {
        this.subordinates = new HashSet<>();
    }
}
