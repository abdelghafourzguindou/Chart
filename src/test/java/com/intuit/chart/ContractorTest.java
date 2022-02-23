package com.intuit.chart;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ContractorTest {

    @Test
    void contractor_should_have_a_manager() {
        Optional<Method> getManager = Arrays.stream(Contractor.class.getMethods())
                .filter(method -> "getManager".equals(method.getName()))
                .findFirst();

        assertThat(getManager).isPresent();

        Contractor contractor = new Contractor();
        Manager manager = new Manager();

        manager.addSubordinate(contractor);

        assertThat(contractor.getManager()).isEqualTo(manager);
        assertThat(manager.getSubordinates()).contains(contractor);
    }

    @Test
    void contractor_should_not_have_subordinates() {
        Optional<Method> getSubordinates = Arrays.stream(Contractor.class.getMethods())
                .filter(method -> "getSubordinates".equals(method.getName()))
                .findFirst();

        assertThat(getSubordinates).isNotPresent();
    }

    @Test
    void contractor_can_change_team() {
        Optional<Method> move = Arrays.stream(Contractor.class.getMethods())
                .filter(method -> "move".equals(method.getName()))
                .findFirst();

        assertThat(move).isPresent();

        Contractor contractor = new Contractor();
        Manager manager1 = new Manager();
        Manager manager2 = new Manager();
        manager1.addSubordinate(contractor);

        assertThat(contractor.getManager()).isEqualTo(manager1);
        assertThat(manager1.getSubordinates()).contains(contractor);
        assertThat(manager2.getSubordinates()).doesNotContain(contractor);

        contractor.move(manager2);

        assertThat(contractor.getManager()).isEqualTo(manager2);
        assertThat(manager2.getSubordinates()).contains(contractor);
        assertThat(manager1.getSubordinates()).doesNotContain(contractor);
    }
}
