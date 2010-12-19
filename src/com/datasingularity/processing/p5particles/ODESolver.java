package com.datasingularity.processing.p5particles;

/**
 * This class epresents an explicit method of integration to solve
 * Newton's equations of motion. You must at least override the
 * integrate(Particle, float) function but you may also need to
 * override the stepBy(float) for some methods.
 *
 * @author Benjamin Eckel
 *
 */
public abstract class ODESolver {

  
    protected ParticleSystem particleSystem;

    public ODESolver(ParticleSystem particleSystem) {
        this.particleSystem = particleSystem;
    }

    /**
     * Steps the simulation by a time-delta (dt)
     *
     * @param h   The change in time to step the simulation by
     */
    public void stepBy(float h) {
        particleSystem.clearForces();
        particleSystem.applyForces();
        for (Particle p : particleSystem.getParticles()) {
            if (!p.fixed) {
                integrate(p, h);
            }
        }
    }

    /**
     * The integration performed per each Particle.
     *
     * @param p     The particle
     * @param h    The change in time, the time-step
     */
    public abstract void integrate(Particle p, float h);
}
