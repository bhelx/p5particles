package com.datasingularity.processing.p5particles;

/**
 * Enumeration that contains all the ODESolvers.
 *
 * @author Benjamin Eckel
 */
public enum ODESolvers {

    RK4(RungeKuttaFourthOrder.class),
    EULER(Euler.class);
    
    public Class solverClass;

    ODESolvers(Class c) {
        solverClass = c;
    }
}
