class FixedBody extends Particle {
 
  public FixedBody(PVector loc) {
      super(loc);
      this.setFixed(true);
      this.setMass(20f);
  }
  
  /**
   * overrides <Particle>.render()
   */
  public void controlledRender() {
    float diam = this.getMass() * MASS_SCALE;
    
    stroke(#B2936B);
    strokeWeight(6f);    
    fill(#FFE8CC);    
    ellipse(this.getLoc().x, this.getLoc().y, diam, diam);
    noStroke();
  }
  
}
