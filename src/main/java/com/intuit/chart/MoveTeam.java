package com.intuit.chart;

/**
 * To be implemented by every Employee that can change team
 * @param <T> Type of Manager to be report to
 */
public interface MoveTeam<T> {

    /**
     * Move team to another team managed by the newManager
     * @param newManager is the new manager to be report to, of type T that should be a manager
     */
    void moveTeam(T newManager);
}
