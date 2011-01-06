import com.datasingularity.processing.p5particles.*;

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

  /*
   * The argument 2f increases the time step by 2 seconds each frame. 
   * This effectively makes the simulation 2 times faster but the larger
   * this number is set the less accurate the simluation becomes. it defaults
   * to 1f.  
   */
  pSystem.tickAndRender(2f);

  drawHUD();
}

void mouseDragged() {
  centerBody.getLoc().x = mouseX;
  centerBody.getLoc().y = mouseY;  
}



