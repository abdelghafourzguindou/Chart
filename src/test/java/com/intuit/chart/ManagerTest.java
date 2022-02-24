package com.intuit.chart;

import org.junit.jupiter.api.Test;
import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ManagerTest extends AbstractEmployeeTest<Manager> {

    private static final Manager manager = new Manager();

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
        director.addSubordinate(manager);

        assertThat(manager.getSubordinates()).contains(contractor);
        assertThat(manager.getManager()).isEqualTo(director);
    }

    @Test
    void manager_can_change_team_without_transferring_their_past_subordinates_to_the_new_team() {
        Optional<Method> move = Arrays.stream(Manager.class.getMethods())
                .filter(method -> "moveTeam".equals(method.getName()))
                .findFirst();

        assertThat(move).isPresent();

        Contractor contractor = new Contractor();
        Permanent permanent1 = new Permanent();
        Permanent permanent2 = new Permanent();
        contractor.setStartDate(LocalDate.now().minusMonths(9));
        permanent1.setStartDate(LocalDate.now().minusMonths(5));
        permanent2.setStartDate(LocalDate.now().minusMonths(2));
        Manager manager = new Manager();
        Director director1 = new Director();
        Director director2 = new Director();

        manager.addSubordinate(contractor);
        manager.addSubordinate(permanent1);
        manager.addSubordinate(permanent2);
        director1.addSubordinate(manager);

        manager.moveTeam(director2);

        assertThat(manager.getSubordinates()).isEmpty();
        assertThat(manager.getManager()).isEqualTo(director2);
        assertThat(director2.getSubordinates()).contains(manager);
        assertThat(director1.getSubordinates()).doesNotContain(manager);
        assertThat(director1.getSubordinates().stream().map(Employee::getId)).contains(permanent1.getId());
    }

    @Override
    protected Manager instance() {
        return manager;
    }

    @Override
    protected BeanTester getBeanTester() {
        BeanTester beanTester = super.getBeanTester();
        beanTester.getFactoryCollection().addFactory(Employee.class, (Factory<Employee>) Director::new);
        return beanTester;
    }
}
