/**
 * <h2>Instructions</h2>
 * <p>
 * Click the screen above and drag to shoot particles from your
 * cursor tip. They will be attracted to the center. Hit the 
 * space bar to toggle drawing of the forces.
 * 
 * <p>
 * @author bhelx 01-07-2011
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
  pSystem.tickAndRender(2f);
  
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
    
    AttractiveForce aForce = pSystem.createAttractiveForce(centerBody, randomBody, 12f, 30f);
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



