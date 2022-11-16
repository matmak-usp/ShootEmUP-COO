package entities.interfaces;

import entities.enums.State;
import libs.GameLib;

public interface Spaceship extends MobileShape {
	
	public Double getExplosionStart();
	public Double getExplosionEnd();
	
	public void setExplosionTime(Long currentTime, Long explosionTime);
	public Long getNextShoot();
	
	default boolean isAbleToShoot(Long currentTime) {
		return currentTime > this.getNextShoot() && this.isActive();
	}
	
	default boolean isActive() {
		return this.getState() == State.INVENCIBLE || this.getBasicMobileShape().isActive(); 
	}

	default void explode(Long currentTime, Long explosionTime) 
	{
		if(this.getState() == State.INVENCIBLE) return;

		this.getBasicMobileShape().setState(State.EXPLODING);
		this.setExplosionTime(currentTime, explosionTime);	
	}
	
	default void drawExplosion(Long currentTime) {
		double alpha = (currentTime - this.getExplosionStart()) / (this.getExplosionEnd() - this.getExplosionStart());
		GameLib.drawExplosion(this.getX(), this.getY(), alpha);
	}
	
	default boolean isExploding() {
		return this.getBasicMobileShape().getState() == State.EXPLODING;
	}
	
	default boolean isOnScreen() {
		return (this.getX() >= 0 && this.getX() <= GameLib.WIDTH) && (this.getY() >= 0 && this.getY() <= GameLib.HEIGHT);
	}
	
	default Spaceship spawn() {
		this.getBasicMobileShape().spawn();
		return this;
	}
	
	public Projectile shoot();
	public void refreshNextShoot(Long currentTime);
}
