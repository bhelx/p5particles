package com.datasingularity.processing.p5particles;

import processing.core.PVector;

/**
 * Should simulate a spring.
 * 
 * @author bhelx
 */
public class SpringForce extends Force {

	private float restLength;
	private float springConstant;
	private float damping;

	protected SpringForce() {
	}

	protected SpringForce(Particle a, Particle b, float springConstant, float restLength, float damping) {
		super();
		setDamping(damping);
		setRestLength(restLength);
		setSpringConstant(springConstant);
		setParticles(a, b);
	}

	public float getRestLength() {
		return restLength;
	}

	public SpringForce setRestLength(float restLength) {
		this.restLength = restLength;
		return this;
	}

	public float getSpringConstant() {
		return springConstant;
	}

	public SpringForce setSpringConstant(float springConstant) {
		this.springConstant = springConstant;
		return this;
	}

	public float getDamping() {
		return damping;
	}

	public SpringForce setDamping(float damping) {
		this.damping = damping;
		return this;
	}

	@Override
	public void apply() {
		if (!a.fixed || !b.fixed) {

			PVector direction = PVector.sub(a.loc, b.loc);
			float dist = direction.mag(); // distance

			float dMinRestTimeConst = (dist - restLength) * springConstant;

			float fx = -(dMinRestTimeConst + damping * (a.vel.x - b.vel.x)
					* direction.x / dist)
					* direction.x / dist;
			float fy = -(dMinRestTimeConst + damping * (a.vel.y - b.vel.y)
					* direction.y / dist)
					* direction.y / dist;
			float fz = -(dMinRestTimeConst + damping * (a.vel.z - b.vel.z)
					* direction.z / dist)
					* direction.z / dist;

			if (!a.fixed) {
				a.applyForce(new PVector(fx, fy, fz));
			}
			if (!b.fixed) {
				b.applyForce(new PVector(-fx, -fy, -fz));
			}
		}
	}

}
