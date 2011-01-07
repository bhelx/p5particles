import com.datasingularity.processing.p5particles.*;

boolean drawForces = true;
boolean moveBox = false;
	
ParticleSystem pSystem; 
ArrayList springs;
Spring chosenSpring;

int NUM_SPRINGS = 30;
float boxSize;

void setup() {
  size(800, 600);
  smooth();
  initHUD();  
  rectMode(CENTER);
  noStroke();

  pSystem = new ParticleSystem();

  springs = new ArrayList();
  
  boxSize = width/(float)NUM_SPRINGS;
  float halfBoxSize = boxSize/2f;
  
  for (float x = halfBoxSize; x < NUM_SPRINGS*boxSize; x += boxSize) {
      springs.add(new Spring(pSystem, new PVector(x, height-halfBoxSize), new PVector(x, height/2), boxSize));
  }
 
}

void draw() {
  background(PALETTE[0]);
  
  drawForces();  
  
  if (moveBox) {
      setSpringByMouse();
      if (chosenSpring != null) chosenSpring.freeBox.setLoc(new PVector(mouseX, mouseY));  
  }
    
  pSystem.tickAndRender();
  
  drawHUD();
}

void setSpringByMouse() {
  for (int i = 0; i < springs.size(); i++) {
    Spring spring = (Spring) springs.get(i);
    float upper = spring.freeBox.getLoc().x + (boxSize/2);
    float lower = spring.freeBox.getLoc().x - (boxSize/2);
    if (mouseX > lower && mouseX < upper) {
       chosenSpring = spring; 
       return;
    }  
  }
  chosenSpring = null;
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


void mousePressed() {
  moveBox = true;
}

void mouseReleased() {
  moveBox = false; 
}

