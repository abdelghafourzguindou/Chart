package com.intuit.chart;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class DirectorTest {

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
        manager.setManager(director);
        vicePresident.addSubordinate(director);
        director.setManager(vicePresident);

        assertThat(director.getSubordinates()).contains(manager);
        assertThat(director.getManager()).isEqualTo(vicePresident);
        assertThat(vicePresident.getSubordinates()).contains(director);
    }

    @Test
    void director_can_change_team_without_transferring_their_past_subordinates_to_the_new_team() {
        Optional<Method> changeTeam = Arrays.stream(Director.class.getMethods())
                .filter(method -> "changeTeam".equals(method.getName()))
                .findFirst();

        assertThat(changeTeam).isPresent();

        Manager manager1 = new Manager();
        Manager manager2 = new Manager();
        Director director = new Director();
        VicePresident vicePresident1 = new VicePresident();
        VicePresident vicePresident2 = new VicePresident();

        manager1.setStartDate(LocalDate.of(2000, 3, 12));
        manager2.setStartDate(LocalDate.of(2019, 2, 22));

        director.addSubordinate(manager1).addSubordinate(manager2);
        manager1.setManager(director);
        manager2.setManager(director);
        vicePresident1.addSubordinate(director);
        director.setManager(vicePresident1);

        director.changeTeam(vicePresident2);

        assertThat(director.getSubordinates()).isEmpty();
        assertThat(director.getManager()).isEqualTo(vicePresident2);
        assertThat(vicePresident2.getSubordinates()).contains(director);
        assertThat(vicePresident1.getSubordinates()).doesNotContain(director);
        assertThat(vicePresident1.getSubordinates().stream().map(Managed::getInfo).map(Employee::getId)).contains(manager1.getId());
    }
}
