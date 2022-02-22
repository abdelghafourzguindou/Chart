package com.intuit.chart;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ManagedPermanentTest {

    @Test
    void ManagedPermanent_should_have_a_manager() {
        Optional<Method> getManager = Arrays.stream(ManagedPermanent.class.getMethods())
                .filter(method -> "getManager".equals(method.getName()))
                .findFirst();

        assertThat(getManager).isPresent();

        ManagedPermanent managedPermanent = new ManagedPermanent();
        Manager manager = new Manager();
        managedPermanent.setManager(manager);
        manager.addSubordinate(managedPermanent);

        assertThat(managedPermanent.getManager()).isEqualTo(manager);
        assertThat(manager.getSubordinates()).contains(managedPermanent);
    }

    @Test
    void ManagedPermanent_should_not_have_subordinates() {
        Optional<Method> getSubordinates = Arrays.stream(ManagedPermanent.class.getMethods())
                .filter(method -> "getSubordinates".equals(method.getName()))
                .findFirst();

        assertThat(getSubordinates).isNotPresent();
    }

    @Test
    void ManagedPermanent_can_change_team() {
        Optional<Method> changeTeam = Arrays.stream(ManagedPermanent.class.getMethods())
                .filter(method -> "changeTeam".equals(method.getName()))
                .findFirst();

        assertThat(changeTeam).isPresent();

        ManagedPermanent managedPermanent = new ManagedPermanent();
        Manager manager1 = new Manager();
        Manager manager2 = new Manager();
        managedPermanent.setManager(manager1);
        manager1.addSubordinate(managedPermanent);

        assertThat(managedPermanent.getManager()).isEqualTo(manager1);
        assertThat(manager1.getSubordinates()).contains(managedPermanent);
        assertThat(manager2.getSubordinates()).doesNotContain(managedPermanent);

        managedPermanent.changeTeam(manager2);

        assertThat(managedPermanent.getManager()).isEqualTo(manager2);
        assertThat(manager2.getSubordinates()).contains(managedPermanent);
        assertThat(manager1.getSubordinates()).doesNotContain(managedPermanent);
    }
}
