package com.datasingularity.processing.p5particles;

import java.util.ArrayList;
import java.util.List;
import processing.core.PVector;

/**
 * This class represents a particles system which may contain a variety of
 * Forces, Particles, and Integrators.
 * 
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
	 * Set the integrator
	 * 
	 * @param odesolver
	 */
	public ParticleSystem setODESolver(ODESolver odesolver) {
		this.integrator = odesolver;
		return this;
	}

	/**
	 * Get the ODEsolver
	 * 
	 * @return
	 */
	public ODESolver getODESolver() {
		return integrator;
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

	public AttractiveForce createAttractiveForce(Particle a, Particle b,
			float g, float minDistance) {
		AttractiveForce force = new AttractiveForce(a, b, g, minDistance);
		addForce(force);
		return force;
	}

	public ConstantGravitationalForce createConstantGravitationalForce(
			Particle a, Particle b, float strength) {
		ConstantGravitationalForce force = new ConstantGravitationalForce(a, b,
				strength);
		addForce(force);
		return force;
	}

	public SpringForce createSpringForce(Particle a, Particle b) {
		SpringForce force = new SpringForce(a, b);
		addForce(force);
		return force;
	}

	public void addForce(Force f) {
		forces.add(f);
	}

	public void removeForce(Force f) {
		forces.remove(f);
	}

	public Force getForce(int i) {
		return forces.get(i);
	}

	public int numForces() {
		return particles.size();
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
	public ArrayList<Particle> getParticles() {
		return particles;
	}

	/**
	 * Get the particle at postion i.
	 * 
	 * @param i
	 * @return the particle at position i
	 */
	public Particle getParticle(int i) {
		return particles.get(i);
	}

	/**
	 * Get the number of particles in the system.
	 * 
	 * @return
	 */
	public int numParticles() {
		return particles.size();
	}

	/**
	 * Creates a generic particle
	 * 
	 * @return the Particle you just created
	 */
	public Particle addParticle() {
		Particle p = new Particle();
		particles.add(p);
		return p;
	}

	/**
	 * Creates a generic particle @ location x, y
	 * 
	 * @return the Particle you just created
	 */
	public Particle addParticle(float x, float y) {
		Particle p = new Particle(new PVector(x, y));
		particles.add(p);
		return p;
	}

	/**
	 * Creates a generic particle @ location x, y, z
	 * 
	 * @return the Particle you just created
	 */
	public Particle addParticle(float x, float y, float z) {
		Particle p = new Particle(new PVector(x, y, z));
		particles.add(p);
		return p;
	}

	/**
	 * Creates a generic particle @ location loc
	 * 
	 * @return the Particle you just created
	 */
	public Particle addParticle(PVector loc) {
		Particle p = new Particle(loc);
		particles.add(p);
		return p;
	}

	/**
	 * Put an existing Particle in the system. p could be any subclass of
	 * Particle.
	 * 
	 * @return the Particle you just put in
	 */
	public Particle addParticle(Particle p) {
		particles.add(p);
		return p;
	}

	/**
	 * Remove a particle from the system.
	 * 
	 * @param p
	 */
	public void removeParticle(Particle p) {
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
	public ParticleSystem setMaxParticles(int maxParticles) {
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
	public void applyForces() {
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

	// /**
	// * Could be used for explicit integration.
	// * @param particle
	// */
	// public void applyForcesThatInvolve(Particle particle) {
	// if (particle.fixed) {
	// return;
	// }
	// for (Force f : getForcesThatInvolve(particle)) {
	// f.apply();
	// }
	// if (globalFriction != 0f) {
	// PVector dissipativeForce = PVector.mult(particle.getVel(),
	// -globalFriction);
	// particle.applyForce(dissipativeForce);
	// }
	// }

	/**
	 * Clear all the forces in the system. This should probably only be used
	 * internally.
	 * 
	 */
	public void clearForces() {
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
}
