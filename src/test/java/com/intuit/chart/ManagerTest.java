package com.intuit.chart;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ManagerTest {

    @Test
    void manager_should_manage_and_managed() {
        Optional<Method> getManager = Arrays.stream(Manager.class.getMethods())
                .filter(method -> "getManager".equals(method.getName()))
                .findFirst();

        Optional<Method> getSubordinates = Arrays.stream(Manager.class.getMethods())
                .filter(method -> "getSubordinates".equals(method.getName()))
                .findFirst();

        assertThat(getManager).isPresent();
        assertThat(getSubordinates).isPresent();

        Contractor contractor = new Contractor();
        Manager manager = new Manager();
        Director director = new Director();

        contractor.setManager(manager);
        manager.addSubordinate(contractor);
        manager.setManager(director);
        director.addSubordinate(manager);

        assertThat(manager.getSubordinates()).contains(contractor);
        assertThat(manager.getManager()).isEqualTo(director);
    }

    @Test
    void director_can_change_team_without_transferring_their_past_subordinates_to_the_new_team() {
        Optional<Method> changeTeam = Arrays.stream(Director.class.getMethods())
                .filter(method -> "changeTeam".equals(method.getName()))
                .findFirst();

        assertThat(changeTeam).isPresent();

        Contractor contractor = new Contractor();
        ManagedPermanent managedPermanent = new ManagedPermanent();
        Manager manager = new Manager();
        Director director1 = new Director();
        Director director2 = new Director();

        contractor.setManager(manager);
        managedPermanent.setManager(manager);
        manager.addSubordinate(contractor).addSubordinate(managedPermanent);
        manager.setManager(director1);
        director1.addSubordinate(manager);

        manager.changeTeam(director2);

        assertThat(manager.getSubordinates()).isEmpty();
        assertThat(manager.getManager()).isEqualTo(director2);
        assertThat(director2.getSubordinates()).contains(manager);
        assertThat(director1.getSubordinates()).doesNotContain(manager);
        assertThat(director2.getSubordinates().stream().map(Managed::getInfo).map(Employee::getId)).contains(manager.getId());
    }
}
