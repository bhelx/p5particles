class Body extends Particle {
  
  public Body(PVector loc) {
      super(loc);
      PVector randomVelocity = new PVector(random(-1f, 1f), random(-1f, 1f));
      this.setVel(randomVelocity);
      this.setCharge(0.5f); //charging for magnetism
      this.setMass(1f); //mass is 1 by default already, just example
  }
  
    
  /**
   * overrides <Particle>.render()
   */
  public void render() {
    float diam = this.getMass() * MASS_SCALE;
   
    fill(PALETTE[1]);
    ellipse(this.getLoc().x, this.getLoc().y, diam, diam);    
    fill(PALETTE[2]);    
    ellipse(this.getLoc().x, this.getLoc().y, diam*0.75, diam*0.75); //75% of diameter
  }
  
}
