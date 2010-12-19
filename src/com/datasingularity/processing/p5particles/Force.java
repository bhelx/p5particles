package com.datasingularity.processing.p5particles;

/**
 *  *
 * Represents a generic Force
 *
 * @author Benjamin Eckel
 *
 */
public interface Force {

    public void apply();
    public boolean involves(Particle p);
    public void setParticles(Particle a, Particle b);

}
