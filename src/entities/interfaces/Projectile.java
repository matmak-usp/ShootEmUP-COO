package entities.interfaces;

import entities.BasicMobileShape;

public interface Projectile extends MobileShape {
	
	public BasicMobileShape getBasicMobileShape();
	
	default boolean isOnScreen() {
		return this.getY() >= 0 && this.getBasicMobileShape().isOnScreen();
	}
	
	default void move(Long delta) {
		this.getBasicMobileShape().move(this.getSpeedX() * delta, this.getSpeedY() * delta);
		this.draw();
	}
}
