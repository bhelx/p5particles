package com.datasingularity.processing.p5particles;

import java.util.ArrayList;
import processing.core.PVector;

/**
 * This class represents a particles system which may contain a variety of
 * Forces, Particles, and Integrators. 
 * 
 * @author bhelx
 */
public class ParticleSystem {

	private float globalFriction;
	private PVector gravity;
	private ArrayList<Force> forces;
	private ArrayList<Particle> particles;
	private ODESolver integrator;

	/**
	 * Default constructor
	 */
	public ParticleSystem() {
		particles = new ArrayList<Particle>();
		forces = new ArrayList<Force>();
		integrator = new RungeKuttaFourthOrder(this);
		// integrator = new Euler(this);
		globalFriction = 0f;
	}

	public ParticleSystem(float globalFriction) {
		this();
		this.globalFriction = globalFriction;
	}

	public ParticleSystem(PVector gravity) {
		this();
		this.gravity = gravity;
	}

	public ParticleSystem(float globalFriction, PVector gravity) {
		this();
		this.gravity = gravity;
		this.globalFriction = globalFriction;
	}

	/**
	 * Get the gravitational vector
	 * 
	 * @return
	 */
	public PVector getGravity() {
		return gravity;
	}

	/**
	 * Set the global gravity vector for this system
	 * 
	 * @param gravity
	 * @return
	 */
	public ParticleSystem setGravity(PVector gravity) {
		this.gravity = gravity;
		return this;
	}

	/**
	 * Set the global gravity vector for this system
	 * 
	 * @param gravity
	 *            a float whcih corresponds to the y axis
	 * @return
	 */
	public ParticleSystem setGravity(float gravity) {
		this.gravity = new PVector(0, gravity, 0);
		return this;
	}

	/**
	 * 
	 * @return float globalFriction
	 */
	public float getGlobalFriction() {
		return globalFriction;
	}

	/**
	 * The global friction is a dissipative force. on every iteration, the force
	 * is calculated by multiplying each particle's velocity by -globalFriciton
	 * then added to the particle.
	 * 
	 * @param globalFriction
	 */
	public ParticleSystem setGlobalFriction(float globalFriction) {
		this.globalFriction = globalFriction;
		return this;
	}

	/**
	 * Return a reference to all the Forces in this particle system.
	 * 
	 * @return ArrayList<Force> forces
	 */
	public ArrayList<Force> getForces() {
		return forces;
	}

	public MagneticForce createMagneticForce(Particle a, Particle b) {
		MagneticForce force = new MagneticForce(a, b);
		addForce(force);
		return force;
	}

	public AttractiveForce createAttractiveForce(Particle a, Particle b, float g, float minDistance) {
		AttractiveForce force = new AttractiveForce(a, b, g, minDistance);
		addForce(force);
		return force;
	}

	public ConstantGravitationalForce createConstantGravitationalForce(Particle a, Particle b, float strength) {
		ConstantGravitationalForce force = new ConstantGravitationalForce(a, b,
				strength);
		addForce(force);
		return force;
	}

	public SpringForce createSpringForce(Particle a, Particle b, float springConstant, float restLength, float damping) {
		SpringForce force = new SpringForce(a, b, springConstant, restLength, damping);
		addForce(force);
		return force;
	}

	public void addForce(Force f) {
		forces.add(f);
	}

	public void removeForce(Force f) {
		forces.remove(f);
	}

	/**
	 * This internally calls ensureCapacity(int) on the ArrayList<Force>. If you
	 * have an application that is adding and/or removing forces often and you
	 * can take a guess at the maximum amount of forces you will have at one
	 * moment, use this.
	 * 
	 * @param maxForces
	 */
	public void setMaxForces(int maxForces) {
		forces.ensureCapacity(maxForces);
	}

	/**
	 * Get all the particles in this system, regardless of class
	 * 
	 * @return particles
	 */
	public final ArrayList<Particle> getParticles() {
		return particles;
	}
	
	/**
	 * Get the number of particles in the system.
	 * 
	 * @return
	 */
	public final int numParticles() {
		return particles.size();
	}


	/**
	 * Put an existing Particle in the system. p could be any subclass of
	 * Particle.
	 * 
	 * @return the Particle you just put in
	 */
	public final Particle addParticle(Particle p) {
		particles.add(p);
		return p;
	}

	/**
	 * Remove a particle from the system.
	 * 
	 * @param p
	 */
	public final void removeParticle(Particle p) {
		particles.remove(p);
	}

	/**
	 * This internally calls ensureCapacity(int) on the ArrayList<Particle> . If
	 * you have an application that is adding and/or removing particles often
	 * and you can take a guess at the maximum amount of forces you will have at
	 * one moment, use this to enhance performance.
	 * 
	 * @param c
	 * @param maxParticles
	 */
	public final ParticleSystem setMaxParticles(int maxParticles) {
		particles.ensureCapacity(maxParticles);
		return this;
	}

	/**
	 * Step the simulation by time step dt;
	 * 
	 * @param dt
	 */
	public final void tick(float dt) {
		removeDead();
		integrator.stepBy(dt);

	}

	/**
	 * Step the simulation by time step 1.
	 * 
	 */
	public final void tick() {
		tick(1f);
	}

	private final void removeDead() {
		for (int i = numParticles() - 1; i >= 0; i--) {
			if (!particles.get(i).alive) {
				particles.remove(i);
			}
		}
	}

	public final void renderParticles() {
		for (int i = 0; i < numParticles(); i++) {
			Particle p = particles.get(i);
			if (p.alive) {
				p.render();
			} else {
				p.kill();
			}
		}
	}

	public final void tickAndRender() {
		tick();
		renderParticles();
	}

	public final void tickAndRender(float dt) {
		tick(dt);
		renderParticles();
	}

	/**
	 * Apply all the Forces in the system. This should probably only be used
	 * internally.
	 * 
	 */
	public final void applyForces() {
		for (Force f : forces) {
			f.apply();
		}
		if (globalFriction != 0f) {
			for (Particle p : getParticles()) {
				if (!p.fixed) {
					PVector dissipativeForce = PVector.mult(p.vel,
							-globalFriction);
					p.applyForce(dissipativeForce);
				}
			}
		}
		if (gravity != null) {
			for (Particle p : getParticles()) {
				if (!p.fixed) {
					p.acc.add(gravity);
				}
			}
		}
	}

	/**
	 * Clear all the forces in the system. This should probably only be used
	 * internally.
	 * 
	 */
	public final void clearForces() {
		for (Particle p : getParticles()) {
			p.clearForce();
		}
	}

	// /**
	// * Get all the forces in which Particle p is a part of.
	// *
	// * @param p the concerned Particle
	// * @return forcesInvolved
	// */
	// private List<Force> getForcesThatInvolve(Particle p) {
	// ArrayList<Force> forcesInvolved = new ArrayList<Force>();
	// forcesInvolved.ensureCapacity(forces.size()); //will at most have this
	// many
	// for (Force f : forces) {
	// if (f.involves(p)) {
	// forcesInvolved.add(f);
	// }
	// }
	// return forcesInvolved;
	// }

	// no use for thse

	// /**
	// * Creates a generic particle
	// *
	// * @return the Particle you just created
	// */
	// public Particle addParticle() {
	// Particle p = new Particle();
	// particles.add(p);
	// return p;
	// }
	//
	// /**
	// * Creates a generic particle @ location x, y
	// *
	// * @return the Particle you just created
	// */
	// public Particle addParticle(float x, float y) {
	// Particle p = new Particle(new PVector(x, y));
	// particles.add(p);
	// return p;
	// }
	//
	// /**
	// * Creates a generic particle @ location x, y, z
	// *
	// * @return the Particle you just created
	// */
	// public Particle addParticle(float x, float y, float z) {
	// Particle p = new Particle(new PVector(x, y, z));
	// particles.add(p);
	// return p;
	// }
	//
	// /**
	// * Creates a generic particle @ location loc
	// *
	// * @return the Particle you just created
	// */
	// public Particle addParticle(PVector loc) {
	// Particle p = new Particle(loc);
	// particles.add(p);
	// return p;
	// }
}
