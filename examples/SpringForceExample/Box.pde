class Box extends Particle {
   
  public Box(PVector loc, float mass) {
      super(loc);  
      setMass(mass);
      setVel(new PVector(random(-3, 3), random(-3, 3)));
  }

    
  /**
   * overrides <Particle>.render()
   */
  public void render() {
    fill(PALETTE[1]);
    rect(this.getLoc().x, this.getLoc().y, mass, mass);    
    fill(PALETTE[2]);    
    rect(this.getLoc().x, this.getLoc().y, mass*0.75, mass*0.75);
  }
  
}
