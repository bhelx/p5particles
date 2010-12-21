package com.datasingularity.processing.p5particles;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Reference:
 * 
 * http://www.flight404.com/p5/magnets3_3D/magnets_3_3D.pde
 *
 *
 * @author bhelx
 */
public class MagneticForce extends Force {

    protected MagneticForce() {}
    
    protected MagneticForce(Particle a, Particle b) {
    	super();
    	setParticles(a, b);
    }

    @Override
    public void apply() {
        if (!a.fixed || !b.fixed) {

            PVector direction = PVector.sub(a.loc, b.loc);
            float distance = direction.mag();
            if (distance > 0.1) {//need a mindistance
                float energy = b.charge / (distance*distance);
                float p = PApplet.abs(a.charge) * PApplet.abs(b.charge) / PApplet.pow(distance, 12);
                float force = (energy * distance) + p;
                direction.div(distance);//normalize
                direction.mult(force);

                if (!b.fixed) {
                    b.applyForce(PVector.mult(direction, -1f));
                }
                if (!a.fixed) {
                    a.applyForce(direction);
                }
            }
        }
    }

}
