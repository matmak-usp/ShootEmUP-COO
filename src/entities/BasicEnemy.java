package entities;

import java.awt.Color;

import entities.enums.ShapeType;
import entities.enums.State;
import entities.interfaces.Enemy;
import entities.interfaces.Projectile;
import libs.GameLib;

public class BasicEnemy implements Enemy {

    private BasicMobileShape mobileShape;

    private double angle;
    private double rotateSpeed = 0;
    private static long nextEnemy;
    private long nextShoot;
    private double explosionStart;
	private double explosionEnd;
	private long shootDelay;
	
	public BasicEnemy(BasicMobileShape mobileShape, double angle, long shootDelay) {
		this.mobileShape = mobileShape;
		
		this.angle = angle;
		this.shootDelay = (long) shootDelay;
		this.setState(State.ACTIVE);
	}
	
	public BasicEnemy(Long currentTime) {
        this.mobileShape = new BasicMobileShape(
        		new BasicShape(Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0, ShapeType.CIRCLE, 12.0, Color.CYAN), 
        		0.20 + Math.random() * 0.015, 
        		0.20 + Math.random() * 0.015
        	);
        nextEnemy = currentTime + 1500;
        this.nextShoot = currentTime + 500;
        this.shootDelay = (long) (200 + Math.random() * 500);
        this.angle = (3 * Math.PI) / 2;
        this.setState(State.ACTIVE);
    }
    
    public void move(Long delta) {    	
    	this.mobileShape.move(0.0, delta * this.getSpeedY());
    }
    
    @Override
    public boolean isOnScreen() {
		return (this.getX() >= 0 && this.getX() <= GameLib.WIDTH) && (this.getY() >= -10 && this.getY() <= GameLib.HEIGHT);
	}

    public double getAngle() {
        return angle;
    }

    public double getRotateSpeed() {
        return rotateSpeed;
    }

    public static long getNextEnemy() {
        return nextEnemy;
    }

    public BasicMobileShape getMobileShape() {
        return mobileShape;
    }

    public Double getExplosionStart() {
		return explosionStart;
	}

	public Double getExplosionEnd() {
		return explosionEnd;
	}

	public void setExplosionTime(Long currentTime, Long explosionDelay) {
		this.explosionStart = currentTime;
		this.explosionEnd = currentTime + explosionDelay;
	}

	public Long getNextShoot() {
		return nextShoot;
	}

	public Projectile shoot() {
		Projectile p = new EnemyProjectile(
				this, 
				Math.cos(this.getAngle()) * 0.9, 
				Math.sin(this.getAngle()) * 0.9 * (-1.0)
			);
		
		p.spawn();
		return p;
	}
	
	public void refreshNextShoot(Long currentTime) {
		this.nextShoot = currentTime + shootDelay;
	}
	
	public BasicEnemy getBasicEnemy() {
		return this;
	}
	
	public void sumAngle(long delta) {
		this.angle += this.rotateSpeed * delta;
	}
	
	public void resetAngle() {
		angle = 0;
	}
	
	public void setRotateSpeed(double rotateSpeed) {
		this.rotateSpeed = rotateSpeed;
	}

	public BasicMobileShape getBasicMobileShape() {
		return mobileShape;
	}
}