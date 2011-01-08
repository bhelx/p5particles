package com.datasingularity.processing.p5particles;

import processing.core.PVector;

/**
 * Solves the ODEs using Euler's method:
 * 
 * References: 
 * + http://en.wikipedia.org/wiki/Euler_method 
 * + http://blog.datasingularity.com/?p=517
 * 
 * @author bhelx
 */
public class Euler extends ODESolver {

	public Euler(ParticleSystem particleSystem) {
		super(particleSystem);
	}

	/**
	 * Simple Euler integration method.
	 */
	@Override
	public void integrate(Particle p, float h) {
		p.vel.add(PVector.mult(p.acc, h));
		p.loc.add(PVector.mult(p.vel, h));
		//now that we are done call the onUpdate()
		p.onUpdate();
	}
}
