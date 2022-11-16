package entities.interfaces;

import entities.BasicEnemy;
import entities.BasicMobileShape;
import libs.GameLib;

public interface Enemy extends Spaceship {
	
    public void move(Long delta);
    public BasicEnemy getBasicEnemy();
    
    default BasicMobileShape getBasicMobileShape() {
    	return this.getBasicEnemy().getBasicMobileShape();
    }
    
    default Double getExplosionStart() {
    	return this.getBasicEnemy().getExplosionStart();
    }
    
    default Double getExplosionEnd() {
    	return this.getBasicEnemy().getExplosionEnd();
    }
    
    default Long getNextShoot() {
    	return this.getBasicEnemy().getNextShoot();
    }
   
    default BasicMobileShape getMobileShape() {
        return this.getBasicEnemy().getMobileShape();
    }
    
    default double getAngle() {
        return this.getBasicEnemy().getAngle();
    }

    default double getRotateSpeed() {
    	return this.getBasicEnemy().getRotateSpeed();
    }
    
    default void setExplosionTime(Long currentTime, Long explosionTime) {
		this.getBasicEnemy().setExplosionTime(currentTime, explosionTime);		
	}
    
    default void refreshNextShoot(Long currentTime) {
    	this.getBasicEnemy().refreshNextShoot(currentTime);
	}
    
    default boolean isOnScreen() {
		return (this.getX() >= 0 && this.getX() <= GameLib.WIDTH) && (this.getY() >= -10 && this.getY() <= GameLib.HEIGHT);
	}
    
	default Projectile shoot() {
		return this.getBasicEnemy().shoot();
	}
	
	default void sumAngle(long delta) {
		this.getBasicEnemy().sumAngle(delta);
	}
	
	default void setRotateSpeed(double rotateSpeed) {
		this.getBasicEnemy().setRotateSpeed(rotateSpeed);
	}
}