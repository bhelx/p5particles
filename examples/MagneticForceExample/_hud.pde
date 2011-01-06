PFont hudFont;

void initHUD() {
  hudFont = createFont("Monospace", 16);
  textFont(hudFont, 16);
}

void drawHUD() { 
  fill(PALETTE[4]);
  text("Particles: " + pSystem.getParticles().size(), 5, 14);
  text("Forces: " + pSystem.getForces().size(), 5, 28);
  text("FPS: " + frameRate, 5, 42);  
}

