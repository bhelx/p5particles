package com.datasingularity.processing.p5particles.test.examples.attractiveforces;

import com.datasingularity.processing.p5particles.AttractiveForce;
import com.datasingularity.processing.p5particles.Particle;
import com.datasingularity.processing.p5particles.ParticleSystem;

import processing.core.PApplet;
import processing.core.PVector;

public class AttractiveForceExample extends PApplet {
	
	private final static int NUM_PARTICLES;
	private final static float TAIL_LENGTH;
	
	private ParticleSystem pSystem; 
	private Particle centerParticle;
	
	static {
		NUM_PARTICLES = 200;
		TAIL_LENGTH = 5f;
	}
	
	@Override
	public void setup() {
		size(600, 400, OPENGL);
		
		pSystem = new ParticleSystem();
		
		centerParticle = new Particle(new PVector(this.width/2f, this.height/2f));
		centerParticle.setFixed(true).setMass(10f);
		pSystem.addParticle(centerParticle);
		
		
		for (int i = 0; i < NUM_PARTICLES; i++) {
			Particle particle = new Particle();
			particle.setLoc(new PVector((float) Math.random() * width, (float) Math.random() * height));
			
			particle.setMass(1f);
			AttractiveForce aForce = pSystem.createAttractiveForce(centerParticle, particle, 30f, 25f);
			
			pSystem.addParticle(particle);
			pSystem.addForce(aForce);
		}
		
	}
	
	@Override
	public void draw() {
		
		background(0);

		strokeWeight(1f);
		fill(255, 255, 255, 150);
		for (Particle p : pSystem.getParticles()) {
			if (!p.equals(centerParticle)) {
				stroke(255);
				line(p.getLoc().x, p.getLoc().y, p.getLoc().x-(p.getVel().x*TAIL_LENGTH), p.getLoc().y-(p.getVel().y*TAIL_LENGTH));
				stroke(255, 0, 0);
				ellipse(p.getLoc().x, p.getLoc().y, p.getMass() * 7f, p.getMass() * 7f);
			}
		}
		
		stroke(0, 0, 255);
		strokeWeight(3f);
		fill(255);
		ellipse(centerParticle.getLoc().x, centerParticle.getLoc().y, centerParticle.getMass() * 5f, centerParticle.getMass() * 5f);
		
		pSystem.tick();
		
	}
	
	@Override
	public void mouseDragged() {
		centerParticle.setVel(pmouseX - mouseX, mouseY - pmouseY);
		centerParticle.setLoc(mouseX, mouseY);
	}
	

}
