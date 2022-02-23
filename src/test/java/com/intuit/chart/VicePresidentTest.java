package com.intuit.chart;

import org.junit.jupiter.api.Test;
import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class VicePresidentTest extends AbstractEmployeeTest<VicePresident> {

    private static final VicePresident vicePresident = new VicePresident();

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
        Director director = new Director();

        ceo.addSubordinate(vicePresident);
        vicePresident.addSubordinate(director);

        assertThat(ceo.getSubordinates()).contains(vicePresident);
        assertThat(vicePresident.getManager()).isEqualTo(ceo);
        assertThat(vicePresident.getSubordinates()).contains(director);
        assertThat(director.getManager()).isEqualTo(vicePresident);
    }

    @Override
    protected VicePresident instance() {
        return vicePresident;
    }

    @Override
    protected BeanTester getBeanTester() {
        BeanTester beanTester = super.getBeanTester();
        beanTester.getFactoryCollection().addFactory(Employee.class, (Factory<Employee>) Ceo::getCeo);
        return beanTester;
    }
}
