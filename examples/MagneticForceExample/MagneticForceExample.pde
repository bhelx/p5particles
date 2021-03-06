/**
 * <h2>Instructions</h2>
 * <p>
 * Click on the center particle and drag to move it.
 * Hit the space bar to toggle drawing of forces.
 * All of the particles in the scene are magnetically charged.
 * The smaller orbiting particles have a constant gravitational
 * attraction to the center particle. 
 * 
 * <p>
 * @author bhelx 01-07-2011
 */
import com.datasingularity.processing.p5particles.*;

boolean drawForces = false;

float MASS_SCALE = 15f;
float R = 100f;
float PI_DIV = 8f;

ParticleSystem pSystem; 
Body centerBody;

void setup() {
  size(800, 600);
  smooth();
  noStroke();

  initHUD();

  pSystem = new ParticleSystem(0.005f);

  centerBody = new Body(new PVector(width/2f, height/2f));
  centerBody.setMass(5f).setCharge(10f).setFixed(true); //charging and fixing

  pSystem.addParticle(centerBody);

  //create particles in a circle, add the constant gravitational force
  for (float theta = 0f; theta < TWO_PI-0.1; theta += (PI/PI_DIV)) {
    //polar to cartesian system
    float x = centerBody.getLoc().x + R * (float) Math.cos(theta);
    float y = centerBody.getLoc().y + R * (float) Math.sin(theta);
  
    Body body = new Body(new PVector(x, y));

    pSystem.addParticle(body);	
    pSystem.createConstantGravitationalForce(centerBody, body, 0.1f);
  }
  
  //add magnetic forces to each other 
  for (Particle particle : pSystem.getParticles()) {
    for (Particle otherParticle :  pSystem.getParticles()) {
      if (!particle.equals(otherParticle)) {
        pSystem.createMagneticForce(particle, otherParticle);
      }
    }
  }
  
}

void draw() {
  background(PALETTE[0]);
  
  drawForces();
  
  // The argument 2f increases the time step by 2 seconds each frame. 
  // This effectively makes the simulation 2 times faster but the larger
  // this number is set the less accurate the simluation becomes. it defaults
  // to 1f.  
  pSystem.tickAndRender(2f);

  drawHUD();
}

void mouseDragged() {
  centerBody.getLoc().x = mouseX;
  centerBody.getLoc().y = mouseY;  
}

void keyPressed() {
  if (key == ' ') drawForces = !drawForces;
}

void drawForces() {
  if (drawForces) {
    ArrayList forces = pSystem.getForces();
    stroke(PALETTE[2], 50);
    for (int i = 0; i < forces.size(); i++) {
      Force f = (Force) forces.get(i);
      if (f instanceof MagneticForce) { //only draw magnetic forces, this is how you can choose
        line(f.getA().getLoc().x, f.getA().getLoc().y, f.getB().getLoc().x, f.getB().getLoc().y);
      }
    }
    noStroke();
  }
}



