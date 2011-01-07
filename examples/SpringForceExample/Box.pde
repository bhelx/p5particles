/**
 * Not very re-usable :)
 */
class Box extends Particle {
   
  float constraintX;
  
  public Box(PVector loc, float mass) {
      super(loc);  
      setMass(mass);
      setVel(new PVector(0f, random(-3, 3)));
      constraintX = loc.x;
  }
    
  /**
   * overrides <Particle>.render()
   */
  public void render() {
    
    this.getLoc().x = constraintX; //constrain
    
    fill(PALETTE[1]);
    rect(this.getLoc().x, this.getLoc().y, mass, mass);    
    fill(PALETTE[2]);    
    rect(this.getLoc().x, this.getLoc().y, mass*0.75, mass*0.75);
  }
  
}
