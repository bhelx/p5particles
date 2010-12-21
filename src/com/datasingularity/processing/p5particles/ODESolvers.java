package com.datasingularity.processing.p5particles;

/**
 * Enumeration that contains all the ODESolvers.
 *
 * @author bhelx
 */
public enum ODESolvers {

    RK4(RungeKuttaFourthOrder.class),
    EULER(Euler.class);
    
    public Class solverClass;

    ODESolvers(Class c) {
        solverClass = c;
    }
}
