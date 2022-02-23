package com.intuit.chart;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PermanentTest {

    @Test
    void ManagedPermanent_should_have_a_manager() {
        Optional<Method> getManager = Arrays.stream(Permanent.class.getMethods())
                .filter(method -> "getManager".equals(method.getName()))
                .findFirst();

        assertThat(getManager).isPresent();

        Permanent managedPermanent = new Permanent();
        Manager manager = new Manager();
        manager.addSubordinate(managedPermanent);

        assertThat(managedPermanent.getManager()).isEqualTo(manager);
        assertThat(manager.getSubordinates()).contains(managedPermanent);
    }

    @Test
    void ManagedPermanent_should_not_have_subordinates() {
        Optional<Method> getSubordinates = Arrays.stream(Permanent.class.getMethods())
                .filter(method -> "getSubordinates".equals(method.getName()))
                .findFirst();

        assertThat(getSubordinates).isNotPresent();
    }

    @Test
    void ManagedPermanent_can_change_team() {
        Optional<Method> move = Arrays.stream(Permanent.class.getMethods())
                .filter(method -> "move".equals(method.getName()))
                .findFirst();

        assertThat(move).isPresent();

        Permanent permanent = new Permanent();
        Manager manager1 = new Manager();
        Manager manager2 = new Manager();

        manager1.addSubordinate(permanent);

        assertThat(permanent.getManager()).isEqualTo(manager1);
        assertThat(manager1.getSubordinates()).contains(permanent);
        assertThat(manager2.getSubordinates()).doesNotContain(permanent);

        permanent.move(manager2);

        assertThat(permanent.getManager()).isEqualTo(manager2);
        assertThat(manager2.getSubordinates()).contains(permanent);
        assertThat(manager1.getSubordinates()).doesNotContain(permanent);
    }
}
