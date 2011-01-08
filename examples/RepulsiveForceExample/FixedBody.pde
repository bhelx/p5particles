class FixedBody extends Particle {
 
  public FixedBody(PVector loc) {
      super(loc);
      this.setFixed(true);
      this.setMass(20f);
  }
  
  /**
   * DOES NOT override <Particle>.render()
   * so you can control when to render.
   * just an example of one way to do things.
   */
  public void controlledRender() {
    float diam = this.getMass() * MASS_SCALE;
    
    stroke(PALETTE[1]);
    strokeWeight(6f);    
    fill(PALETTE[2]);    
    ellipse(this.getLoc().x, this.getLoc().y, diam, diam);
    noStroke();
  }
  
}
