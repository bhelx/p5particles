public class Spring {
  
  private ParticleSystem myParticleSystem;
  public Box fixedBox;
  public Box freeBox;
  
  public Spring(ParticleSystem _pSystem, PVector fixedBoxCoords, PVector freeBoxCoords) {
    myParticleSystem = _pSystem;
    
    fixedBox = new Box(fixedBoxCoords, 30f);
    fixedBox.setFixed(true);
    myParticleSystem.addParticle(fixedBox);
    
    freeBox = new Box(freeBoxCoords, 30f);
    myParticleSystem.addParticle(freeBox);    
   
    float restLength = dist(fixedBoxCoords.x, fixedBoxCoords.y, freeBoxCoords.x, freeBoxCoords.y);
    
    myParticleSystem.createSpringForce(fixedBox, freeBox, 0.8f, restLength, 2f);
   
  }
  
}
