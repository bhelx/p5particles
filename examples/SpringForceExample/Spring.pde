public class Spring {
  
  private ParticleSystem myParticleSystem;
  private Box fixedBox;
  private Box freeBox;
  
  public Spring(ParticleSystem _pSystem, PVector fixedBoxCoords, PVector freeBoxCoords) {
    myParticleSystem = _pSystem;
    
    fixedBox = new Box(fixedBoxCoords, 30f);
    fixedBox.setFixed(true);
    myParticleSystem.addParticle(fixedBox);
    
    freeBox = new Box(freeBoxCoords, 30f);
    myParticleSystem.addParticle(freeBox);    
    
    myParticleSystem.createSpringForce(fixedBox, freeBox, 1f, dist(fixedBoxCoords.x, fixedBoxCoords.y, freeBoxCoords.x, freeBoxCoords.y), 0.9f);
    
  }
  
}
