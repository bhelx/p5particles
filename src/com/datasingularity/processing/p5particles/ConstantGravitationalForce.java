package com.datasingularity.processing.p5particles;

import processing.core.PVector;

/**
 * 
 * @author bhelx
 */
public class ConstantGravitationalForce extends Force {

	private float g;

	protected ConstantGravitationalForce() {
	}

	protected ConstantGravitationalForce(Particle a, Particle b, float g) {
		super();
		setParticles(a, b);
		this.g = g;
	}

	@Override
	public void apply() {
		if (!b.fixed) {
			PVector forceVector = PVector.sub(a.loc, b.loc);
			if (forceVector.mag() > 0.1) {
				forceVector.normalize();
				forceVector.mult(g);
				// this is constant, doesnt care about mass
				b.getAcc().add(forceVector);
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
