class Body extends Particle {

  float TAIL_LENGTH = 10f;
  float TAIL_WIDTH = 3f;  
  float MIN_MASS = 3f;
  float MAX_MASS = 6f;
  
  public Body(PVector loc) {
      super(loc);
      PVector randomVelocity = new PVector(random(-1f, 1f), random(-1f, 1f));
      this.setVel(randomVelocity);
      this.setMass(random(MIN_MASS, MAX_MASS));
  }
  
  /**
   * overrides <Particle>.render()
   */
  public void render() {
    float diam = this.getMass() * MASS_SCALE;
    
    stroke(PALETTE[3]);
    strokeWeight(TAIL_WIDTH);
    line(this.getLoc().x, this.getLoc().y, this.getLoc().x-(this.getVel().x*TAIL_LENGTH), this.getLoc().y-(this.getVel().y*TAIL_LENGTH));
    noStroke();
   
    fill(PALETTE[1]);
    ellipse(this.getLoc().x, this.getLoc().y, diam, diam);    
    fill(PALETTE[2]);    
    ellipse(this.getLoc().x, this.getLoc().y, diam*0.75, diam*0.75); //75% of diameter
  }
  
}
