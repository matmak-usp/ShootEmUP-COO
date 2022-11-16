package entities;

import entities.interfaces.PowerUp;
import entities.interfaces.Projectile;
import entities.interfaces.Spaceship;

public class Player implements Spaceship {
	
	private BasicMobileShape mobileShape;
	
	private Double explosionStart;
	private Double explosionEnd;
	private Long nextShoot;
	private PowerUp pickedPowerUp;
	
	private Long shootDelay;

	public Player(BasicMobileShape mobileShape, Long nextShoot) {
		this.mobileShape = mobileShape;
		this.explosionStart = 0.0;
		this.explosionEnd = 0.0;
		this.nextShoot = nextShoot;
		
		this.shootDelay = (long) 100;
		
		this.spawn();
	}

	
	public void setExplosionTime(Long currentTime, Long explosionDelay) {
		this.explosionStart = (double) currentTime;
		this.explosionEnd = (double) currentTime + explosionDelay;
	}

	public Projectile shoot() 
	{
		Projectile p = new PlayerProjectile(
				this.getX(),
				this.getY() - 2 * this.getRadius()
			);
		p.spawn();
		
		return p;
	}
	
	public void refreshNextShoot(Long currentTime) {
		this.nextShoot = currentTime + this.getShootDelay();
	}
	
	public void moveUp(Long delta) {
		if (!this.isActive())
			return;
		mobileShape.move(0.0, this.getSpeedY() * delta * -1);
	}
	
	public void moveDown(Long delta) {
		if (!this.isActive())
			return;
		mobileShape.move(0.0, this.getSpeedY() * delta);
	}
	
	public void moveLeft(Long delta) {
		if (!this.isActive())
			return;
		mobileShape.move(this.getSpeedX()  * delta * -1, 0.0);
	}
	
	public void moveRight(Long delta) {
		if (!this.isActive())
			return;
		mobileShape.move(this.getSpeedX() * delta, 0.0);
	}

	public BasicMobileShape getBasicMobileShape() {
		return mobileShape;
	}

	public Double getExplosionStart() {
		return explosionStart;
	}

	public Double getExplosionEnd() {
		return explosionEnd;
	}

	public Long getNextShoot() {
		return nextShoot;
	}
	
	public Long getShootDelay() {
		return shootDelay;
	}

	public void setPickedPowerUp(PowerUp powerUp) {
		this.pickedPowerUp = powerUp;
	}

	public PowerUp getPickedPowerUp() {
		return this.pickedPowerUp;
	}
	
	public boolean isPoweredUp() {
		return pickedPowerUp != null;
	}
}
