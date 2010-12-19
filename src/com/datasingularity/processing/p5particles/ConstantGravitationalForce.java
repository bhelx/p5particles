package com.datasingularity.processing.p5particles;

import processing.core.PVector;

/**
 *
 * @author Benjamin Eckel
 */
public class ConstantGravitationalForce implements Force {

    private Particle a;
    private Particle b;
    private float g;

    protected ConstantGravitationalForce() {}
    
    protected ConstantGravitationalForce(Particle a, Particle b) {
        this.a = a;
        this.b = b;
    }

    protected ConstantGravitationalForce(Particle a, Particle b, float g) {
        this.a = a;
        this.b = b;
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

    @Override
    public boolean involves(Particle p) {
        return p.equals(a) || p.equals(b);
    }

    @Override
    public void setParticles(Particle a, Particle b) {
    	this.a = a;
    	this.b = b;
    }

    public Particle getA() {
        return a;
    }

    public ConstantGravitationalForce setA(Particle a) {
        this.a = a;
        return this;
    }

    public Particle getB() {
        return b;
    }

    public ConstantGravitationalForce setB(Particle b) {
        this.b = b;
        return this;
    }

    public float getStrength() {
        return g;
    }

    public ConstantGravitationalForce setStrength(float g) {
        this.g = g;
        return this;
    }

    public void swapParticles(Particle original, Particle newP) {
        if (a.equals(original)) {
            a = newP;
        } else if (b.equals(original)) {
            b = newP;
        }
    }
}

