package com.datasingularity.processing.p5particles;

import java.util.ArrayList;

import processing.core.PVector;

/**
 * Implements an RK4 integrator.
 *
 * References:
 *
 *  http://www.particle.kth.se/~lindsey/JavaCourse/Book/Part1/Physics/Chapter04/RungeKutta4th.html
 *
 * @author Benjamin Eckel
 */
public class RungeKuttaFourthOrder extends ODESolver {

    public RungeKuttaFourthOrder(ParticleSystem particleSystem) {
        super(particleSystem);
    }

    @Override
    public void stepBy(float h) {
    	
    	final ArrayList<Particle> particles = particleSystem.getParticles();
    	final int numParticles = particles.size();
    	final float halfH = h * 0.5f;
    	
        //store original position and velocity values
        final PVector[] originalVelocities = new PVector[numParticles];
        final PVector[] originalPositions = new PVector[numParticles];
        final PVector[] k1Accs = new PVector[numParticles];
        final PVector[] k1Vels = new PVector[numParticles];
        final PVector[] k2Accs = new PVector[numParticles];
        final PVector[] k2Vels = new PVector[numParticles];
        final PVector[] k3Accs = new PVector[numParticles];
        final PVector[] k3Vels = new PVector[numParticles];
        final PVector[] k4Accs = new PVector[numParticles];
        final PVector[] k4Vels = new PVector[numParticles];
        
        for (int i = 0; i < numParticles; i++) {
        	final Particle particle = particles.get(i);
        	
        	if (!particle.fixed) {
        		originalPositions[i] = particle.loc.get();
        		originalVelocities[i] = particle.vel.get();
		    	particle.clearForce();
        	}
        }
    	
        //apply forces 
        particleSystem.applyForces();
        
        //store first derivatives, derivative at Xn
        for (int i = 0; i < numParticles; i++) {
        	final Particle particle = particles.get(i);
        	
        	if (!particle.fixed) {
        		k1Accs[i] = particle.acc.get();
        		k1Vels[i] = particle.vel.get();
	        	particle.clearForce();
        	}
        }
        
        //apply calculations
        for (int i = 0; i < numParticles; i++) {
            final Particle particle = particles.get(i);
            
            if (!particle.fixed) {	            
	            particle.setLoc(PVector.add(originalPositions[i], PVector.mult(k1Vels[i], halfH)));
	            particle.setVel(PVector.add(originalVelocities[i], PVector.div(PVector.mult(k1Accs[i], halfH), particle.mass)));
            }
        }
        
        //apply forces
        particleSystem.applyForces();
        
        //store k2s
        for (int i = 0; i < numParticles; i++) {
        	final Particle particle = particles.get(i);
        	
        	if (!particle.fixed) {
        		k2Accs[i] = particle.acc.get();
        		k2Vels[i] = particle.vel.get();
        		particle.clearForce();
        	}
        }
        
        //now k3s
        for (int i = 0; i < numParticles; i++) {
            final Particle particle = particles.get(i);
            
            if (!particle.fixed) {
            	particle.setLoc(PVector.add(originalPositions[i], PVector.mult(k2Vels[i], halfH)));
            	particle.setVel(PVector.add(originalVelocities[i], PVector.div(PVector.mult(k2Accs[i], halfH), particle.mass)));
            }
        }
        
        particleSystem.applyForces();
        
        //store them in k3
        for (int i = 0; i < numParticles; i++) {
            final Particle particle = particles.get(i);
            
            if (!particle.fixed) {
	        	k3Accs[i] = particle.acc.get();
	        	k3Vels[i] = particle.vel.get();
	        	particle.clearForce();
            }
        }
        
        //now k4s
        for (int i = 0; i < numParticles; i++) {
            final Particle particle = particles.get(i);
            
            if (!particle.fixed) {
            	particle.setLoc(PVector.add(originalPositions[i], PVector.mult(k3Vels[i], h)));
            	particle.setVel(PVector.add(originalVelocities[i], PVector.div(PVector.mult(k3Accs[i], h), particle.mass)));
            }
        }
        
        particleSystem.applyForces();
        
        //store k4s
        for (int i = 0; i < numParticles; i++) {
            final Particle particle = particles.get(i);
            
            if (!particle.fixed) {
	        	k4Accs[i] = particle.acc.get();
	        	k4Vels[i] = particle.vel.get();
	        	
	        	particle.clearForce();
            }
        }
        
        //final calculations
        for (int i = 0; i < numParticles; i++) {
            final Particle particle = particles.get(i);
            
            if (!particle.fixed) {
            	final PVector k2Vel = PVector.mult(k2Vels[i], 2f);
            	final PVector k3Vel = PVector.mult(k3Vels[i], 2f);
            	
            	final PVector kVelSum = PVector.add(PVector.add(PVector.add(k1Vels[i], k2Vel), k3Vel), k4Vels[i]);
            	kVelSum.mult(h/6f);
            	
            	particle.setLoc(PVector.add(originalPositions[i], kVelSum));
 
            	final PVector k2Acc = PVector.mult(k2Accs[i], 2f);
            	final PVector k3Acc = PVector.mult(k3Accs[i], 2f);
            	
            	final PVector kAccSum = PVector.add(PVector.add(PVector.add(k1Accs[i], k2Acc), k3Acc), k4Accs[i]);
            	kAccSum.mult(h/6f);
	            
            	particle.setVel(PVector.add(originalVelocities[i], kAccSum));
            }
            
        }
    }

    @Override
    public void integrate(Particle p, float dt) {
    }
    
}
