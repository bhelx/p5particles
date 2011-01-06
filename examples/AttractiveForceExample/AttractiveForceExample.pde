import com.datasingularity.processing.p5particles.*;

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
    
  /*
   * The argument 2f increases the time step by 2 seconds each frame. 
   * This effectively makes the simulation 2 times faster but the larger
   * this number is set the less accurate the simluation becomes. it defaults
   * to 1f.  
   */
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



