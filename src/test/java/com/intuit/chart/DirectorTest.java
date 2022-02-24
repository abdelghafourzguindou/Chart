package com.intuit.chart;

import org.junit.jupiter.api.Test;
import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class DirectorTest extends AbstractEmployeeTest<Director> {

    private static final Director director = new Director();

    @Test
    void director_should_manage_and_managed() {
        Optional<Method> getManager = Arrays.stream(Director.class.getMethods())
                .filter(method -> "getManager".equals(method.getName()))
                .findFirst();

        Optional<Method> getSubordinates = Arrays.stream(Director.class.getMethods())
                .filter(method -> "getSubordinates".equals(method.getName()))
                .findFirst();

        assertThat(getManager).isPresent();
        assertThat(getSubordinates).isPresent();

        Manager manager = new Manager();
        Director director = new Director();
        VicePresident vicePresident = new VicePresident();
        director.addSubordinate(manager);
        vicePresident.addSubordinate(director);

        assertThat(director.getSubordinates()).contains(manager);
        assertThat(director.getManager()).isEqualTo(vicePresident);
        assertThat(vicePresident.getSubordinates()).contains(director);
    }

    @Test
    void director_can_change_team_without_transferring_their_past_subordinates_to_the_new_team() {
        Optional<Method> move = Arrays.stream(Director.class.getMethods())
                .filter(method -> "moveTeam".equals(method.getName()))
                .findFirst();

        assertThat(move).isPresent();

        Contractor contractor = new Contractor();
        Permanent permanent1 = new Permanent();
        Permanent permanent2 = new Permanent();
        Manager manager1 = new Manager();
        Manager manager2 = new Manager();
        Director director1 = new Director();
        Director director2 = new Director();
        VicePresident vicePresident1 = new VicePresident();
        VicePresident vicePresident2 = new VicePresident();

        contractor.setStartDate(LocalDate.now().minusMonths(9));
        permanent1.setStartDate(LocalDate.now().minusMonths(5));
        permanent2.setStartDate(LocalDate.now().minusMonths(2));
        manager1.setStartDate(LocalDate.of(2000, 3, 12));
        manager2.setStartDate(LocalDate.of(2019, 2, 22));

        manager1.addSubordinate(contractor);
        manager1.addSubordinate(permanent1);
        manager2.addSubordinate(permanent2);
        director1.addSubordinate(manager1);
        director2.addSubordinate(manager2);
        vicePresident1.addSubordinate(director1);
        vicePresident1.addSubordinate(director2);

        director1.moveTeam(vicePresident2);

        assertThat(director1.getSubordinates()).isEmpty();
        assertThat(director1.getManager()).isEqualTo(vicePresident2);
        assertThat(vicePresident2.getSubordinates()).contains(director1);
        assertThat(vicePresident1.getSubordinates()).doesNotContain(director1);
        assertThat(vicePresident1.getSubordinates().stream().map(Employee::getId)).contains(manager1.getId());

        Set<UUID> managerIds = vicePresident1.getSubordinates()
                .stream()
                .filter(director -> director.getId().equals(manager1.getId()))
                .map(Director::getSubordinates)
                .flatMap(Collection::stream)
                .map(Employee::getId)
                .collect(Collectors.toSet());

        assertThat(managerIds).contains(permanent1.getId());
    }

    @Override
    protected Director instance() {
        return director;
    }

    @Override
    protected BeanTester getBeanTester() {
        BeanTester beanTester = super.getBeanTester();
        beanTester.getFactoryCollection().addFactory(Employee.class, (Factory<Employee>) VicePresident::new);
        return beanTester;
    }
}
