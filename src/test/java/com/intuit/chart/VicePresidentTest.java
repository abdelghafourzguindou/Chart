package com.intuit.chart;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class VicePresidentTest {

    @Test
    void vice_president_should_manage_and_managed() {
        Optional<Method> getManager = Arrays.stream(VicePresident.class.getMethods())
                .filter(method -> "getManager".equals(method.getName()))
                .findFirst();

        Optional<Method> getSubordinates = Arrays.stream(Manager.class.getMethods())
                .filter(method -> "getSubordinates".equals(method.getName()))
                .findFirst();

        assertThat(getManager).isPresent();
        assertThat(getSubordinates).isPresent();

        Ceo ceo = Ceo.getCeo();
        VicePresident vicePresident = new VicePresident();
        Director director = new Director();

        ceo.addSubordinate(vicePresident);
        vicePresident.addSubordinate(director);
        director.setManager(vicePresident);
        vicePresident.setManager(ceo);

        assertThat(ceo.getSubordinates()).contains(vicePresident);
        assertThat(vicePresident.getManager()).isEqualTo(ceo);
        assertThat(vicePresident.getSubordinates()).contains(director);
        assertThat(director.getManager()).isEqualTo(vicePresident);
    }

    @Test
    void vice_president_can_not_change_team() {
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> new VicePresident().changeTeam(Ceo.getCeo()));
    }
}
