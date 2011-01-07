import com.datasingularity.processing.p5particles.*;

boolean drawForces = false;

int NUM_BODIES = 10;
float MASS_SCALE = 3f;
	
ParticleSystem pSystem; 
Spring spring;

void setup() {
  size(800, 600);
  smooth();
  initHUD();  
  rectMode(CENTER);

  pSystem = new ParticleSystem(0.005f);

  spring = new Spring(pSystem, new PVector(width/2, height), new PVector(width/2, height/2));
 
}

void draw() {
  background(PALETTE[0]);
  
  drawForces();  
    
  pSystem.tickAndRender();
  
  drawHUD();
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



