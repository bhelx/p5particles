package com.datasingularity.processing.p5particles;

import processing.core.PVector;

public class AttractiveForce implements Force {

    private Particle a;
    private Particle b;
    private float g;
    private float minDistance;

    protected AttractiveForce() {}
    
    protected AttractiveForce(Particle a, Particle b) {
        this.a = a;
        this.b = b;
    }
    
    protected AttractiveForce(Particle a, Particle b, float g, float minDistance) {
        this.a = a;
        this.b = b;
        this.g = g;
        this.minDistance = minDistance;
    }

    @Override
    public void apply() {
        PVector forceVector = PVector.sub(a.loc, b.loc);
        float d = forceVector.mag();
        if (d < minDistance) {
            d = minDistance;
        }
        forceVector.normalize();
        float force = (g * a.mass * b.mass) / (d * d);
        forceVector.mult(force);
        if (!b.isFixed()) {
            b.applyForce(forceVector);
        }
        if (!a.isFixed()) {
            a.applyForce(PVector.mult(forceVector, -1f));
        }

    }

    @Override
    public void setParticles(Particle a, Particle b) {
    	this.a = a;
    	this.b = b;
    }
    
    @Override
    public boolean involves(Particle p) {
        return p.equals(a) || p.equals(b);
    }

    public Particle getA() {
        return a;
    }

    public AttractiveForce setA(Particle a) {
        this.a = a;
        return this;
    }

    public Particle getB() {
        return b;
    }

    public AttractiveForce setB(Particle b) {
        this.b = b;
        return this;
    }

    public float getStrength() {
        return g;
    }

    public AttractiveForce setStrength(float g) {
        this.g = g;
        return this;
    }

    public float getMinDistance() {
        return minDistance;
    }

    public AttractiveForce setMinDistance(float minDistance) {
        this.minDistance = minDistance;
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
