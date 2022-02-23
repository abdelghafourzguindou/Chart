package com.intuit.chart;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

class CeoTest extends AbstractEmployeeTest<Ceo> {

    @Test
    void ceo_should_be_one_instance() {
        assertSame(Ceo.getCeo(), Ceo.getCeo());
    }

    @Test
    void ceo_should_not_have_manager() {
        Optional<Method> getManager = Arrays.stream(Ceo.class.getMethods())
                .filter(method -> "getManager".equals(method.getName()))
                .findFirst();

        assertThat(getManager).isNotPresent();
    }

    @Test
    void ceo_should_have_vice_president_subordinates() {
        Optional<Method> getSubordinates = Arrays.stream(Ceo.class.getMethods())
                .filter(method -> "getSubordinates".equals(method.getName()))
                .findFirst();

        assertThat(getSubordinates).isPresent();

        Ceo ceo = Ceo.getCeo();
        VicePresident vicePresident = new VicePresident();

        ceo.addSubordinate(vicePresident);

        assertThat(ceo.getSubordinates()).contains(vicePresident);
        assertThat(vicePresident.getManager()).isEqualTo(ceo);
    }

    @Override
    protected Ceo instance() {
        return Ceo.getCeo();
    }
}
