package com.datasingularity.processing.p5particles.test.examples.magneticforces;

import com.datasingularity.processing.p5particles.ConstantGravitationalForce;
import com.datasingularity.processing.p5particles.MagneticForce;
import com.datasingularity.processing.p5particles.Particle;
import com.datasingularity.processing.p5particles.ParticleSystem;

import processing.core.PApplet;
import processing.core.PVector;

public class MagneticForcesExample extends PApplet {
	
	private final static int NUM_PARTICLES;
	private final static float R;
	
	private ParticleSystem pSystem; 
	private Particle centerParticle;
	
	static {
		NUM_PARTICLES = 200;
		R = 80f;
	}
	
	@Override
	public void setup() {
		size(800, 600, OPENGL);
		
		pSystem = new ParticleSystem(0.01f);
		
		centerParticle = new Particle(new PVector(this.width/2f, this.height/2f));
		centerParticle.setFixed(true).setMass(10f).setCharge(10f);
		pSystem.addParticle(centerParticle);
		
		
		//create particles and add constant gravitational force
		for (float theta = 0f; theta < TWO_PI-0.1; theta += (PI/8f)) {
			Particle particle = new Particle();
			particle.setCharge(0.5f).setMass(1f);
			
			final float x = centerParticle.getLoc().x + R * (float) Math.cos(theta);
			final float y = centerParticle.getLoc().y + R * (float) Math.sin(theta);
			
			particle.setLoc(x, y);
		
			pSystem.addParticle(particle);	
			pSystem.createConstantGravitationalForce(centerParticle, particle, 0.1f);
		}
		
		//create magnetic attraction to centerparticle
		for (Particle particle : pSystem.getParticles()) {
			if (!particle.equals(centerParticle)) {
				pSystem.createMagneticForce(particle, centerParticle);
			}			
		}
		
		//add magnetic forces to each other 
		for (Particle particle : pSystem.getParticles()) {
			if (!particle.equals(centerParticle)) {
				for (Particle otherParticle :  pSystem.getParticles()) {
					if (!otherParticle.equals(particle) && !otherParticle.equals(centerParticle)) {
						pSystem.createMagneticForce(particle, otherParticle);
					}
				}
			}
		}
		
	}
	
	@Override
	public void draw() {
		
		background(0);

		strokeWeight(1f);
		fill(255, 255, 255, 150);
		for (Particle p : pSystem.getParticles()) {
			if (!p.equals(centerParticle)) {
				stroke(255, 0, 0);
				ellipse(p.getLoc().x, p.getLoc().y, p.getMass() * 7f, p.getMass() * 7f);
			}
		}
		
		stroke(0, 0, 255);
		strokeWeight(3f);
		fill(255);
		ellipse(centerParticle.getLoc().x, centerParticle.getLoc().y, centerParticle.getMass() * 5f, centerParticle.getMass() * 5f);
		
		//each time step by 2, makes simulation faster
		pSystem.tick(2f);
		
	}
	
	@Override
	public void mouseDragged() {
		centerParticle.setVel(pmouseX - mouseX, mouseY - pmouseY);
		centerParticle.setLoc(mouseX, mouseY);
	}
	
	
	

}
