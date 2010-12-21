package com.datasingularity.processing.p5particles;

import processing.core.PVector;

public class AttractiveForce extends Force {

    private float g;
    private float minDistance;

    protected AttractiveForce() {}
    
    protected AttractiveForce(Particle a, Particle b, float g, float minDistance) {
       	super();
        setParticles(a, b);
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

}
