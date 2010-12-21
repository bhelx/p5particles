package com.datasingularity.processing.p5particles;

import processing.core.PVector;

/**
 *
 * @author bhelx
 */
public class ConstantGravitationalForce extends Force {

    private float g;

    protected ConstantGravitationalForce() {}
   
    protected ConstantGravitationalForce(Particle a, Particle b, float g) {
    	super();
    	setParticles(a, b);
        this.g = g;
    }
    
    @Override
    /**
     * b is the fixed point 
     *
     */
    public void apply() {
        if (!b.fixed) {
            PVector forceVector = PVector.sub(a.loc, b.loc);
            if (forceVector.mag() > 0.1) {
                forceVector.normalize();
                forceVector.mult(g);
                b.getAcc().add(forceVector);//this is constant, doesnt care about mass
            }
        }
    }

    public float getStrength() {
        return g;
    }

    public ConstantGravitationalForce setStrength(float g) {
        this.g = g;
        return this;
    }

}

