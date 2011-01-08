/**
 * <h2>Instructions</h2>
 *
 * <p>
 * Click the screen above and drag to shoot particles from your
 * cursor tip. They will be pushed away from the center. Hit the 
 * space bar to toggle drawing of the forces.
 *
 * <p>
 * Repulsion is the same force as attraction, 
 * just a different direction.
 * 
 * <p>
 * author bhelx 01-07-2011
 *
 */
import com.datasingularity.processing.p5particles.*;

boolean drawForces = false;

int NUM_BODIES = 10;
float MASS_SCALE = 3f;

ParticleSystem pSystem; 
FixedBody centerBody;

void setup() {
  size(800, 600);
  smooth();
  initHUD();  

  pSystem = new ParticleSystem(0.005f);

  centerBody = new FixedBody(new PVector(width/2f, height/2f));

  pSystem.addParticle(centerBody);
}

void draw() {
  background(PALETTE[0]);

  drawForces();  

  
  // The argument 2f increases the time step by 2 seconds each frame. 
  // This effectively makes the simulation 2 times faster but the larger
  // this number is set the less accurate the simluation becomes. it defaults
  // to 1f.  
  pSystem.tick(2f); //just tick(), no render() yet

  //bounce them off the walls
  ArrayList particles = pSystem.getParticles();
  for (int i = 0; i < particles.size(); i++) {
    Particle p = (Particle) particles.get(i);
    if (p instanceof Body) { //only need to check these
      trapParticle(p);
    }
  }

  //now render particles, this excludes centerParticle b/c it does not override render()
  pSystem.renderParticles();

  //rendering on our own terms
  centerBody.controlledRender();

  drawHUD();
}

void mouseDragged() {
  PVector mouseVelocity = new PVector(mouseX-pmouseX, mouseY-pmouseY);
  mouseVelocity.mult(0.1f); //scale it down

  Body randomBody = new Body(new PVector(mouseX, mouseY)); 
  randomBody.setVel(mouseVelocity);

  pSystem.addParticle(randomBody);

  //make the force -12f as opposed to 12f
  AttractiveForce aForce = pSystem.createAttractiveForce(centerBody, randomBody, -12f, 30f);
}


void keyPressed() {
  if (key == ' ') drawForces = !drawForces;
}

void drawForces() {
  if (drawForces) {
    ArrayList forces = pSystem.getForces();
    stroke(PALETTE[2], 50);
    strokeWeight(1f);
    for (int i = 0; i < forces.size(); i++) {
      Force f = (Force) forces.get(i);
      line(f.getA().getLoc().x, f.getA().getLoc().y, f.getB().getLoc().x, f.getB().getLoc().y);
    }
    noStroke();
  }
}

void trapParticle(Particle p) {
        float leftWall = 0f + p.getMass()/2f;
      float rightWall = width - p.getMass()/2f;
      float topWall = leftWall;
      float bottomWall = height - p.getMass()/2f;
    
      if(p.getLoc().x < leftWall) { 
        p.getVel().x = -p.getVel().x;
        p.getLoc().x = leftWall;
      } 
      else if(p.getLoc().x > rightWall) {
        p.getVel().x = -p.getVel().x;
        p.getLoc().x = rightWall;
      }
      
      if(p.getLoc().y < topWall) { 
        p.getVel().y = -p.getVel().y;
        p.getLoc().y = topWall;
      } 
      else if(p.getLoc().y > bottomWall) {
        p.getVel().y = -p.getVel().y;
        p.getLoc().y = bottomWall;
      } 
} 


