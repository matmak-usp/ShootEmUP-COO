package entities.interfaces;

import entities.BasicMobileShape;
import entities.Player;
import libs.GameLib;

public interface PowerUp extends MobileShape{
	
	public BasicMobileShape getBasicMobileShape();
	public void toPowerUp(Player player, long currentTime);
	public void setPowerUpEnd(long currentTime);
	public void resetState(Player player);
	
	default boolean isOnScreen() {
		return this.getY() >= -10 && this.getY() <= GameLib.HEIGHT;
	}
	
	default void move(Long delta) {
		this.getBasicMobileShape().move(this.getSpeedX() * delta, this.getSpeedY() * delta);
		this.draw();
	}
	
	default PowerUp spawn() {
		this.getBasicMobileShape().spawn();
		return this;
	}

	public long getPowerUpEnd();
}
