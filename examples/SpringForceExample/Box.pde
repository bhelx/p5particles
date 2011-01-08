/**
 * Not very re-usable :)
 * Hopefully a good jumping off point.
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
    
    if (this.getLoc().y > height - mass) {
        this.getLoc().y = height - mass; //keep it from going under fixed box
    }
    
    fill(PALETTE[1]);
    rect(this.getLoc().x, this.getLoc().y, mass, mass);    
    fill(PALETTE[2]);    
    rect(this.getLoc().x, this.getLoc().y, mass*0.75, mass*0.75);
  }
  
}
