package entities;

import java.awt.Color;

import entities.enums.ShapeType;
import entities.interfaces.Enemy;
import entities.interfaces.Projectile;
import libs.GameLib;

public class CombinedEnemies implements Enemy {

	private BasicEnemy basicEnemy;
	
    private static long nextEnemy;
    private boolean ableToShoot;
    private static int countEnemies = 0;
    private static int countShoots = 0;
    private static double rotationDirection;

    public CombinedEnemies(Long currentTime) {
    	if (countEnemies == 0)
    		rotationDirection = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
    	
    	this.basicEnemy = new BasicEnemy(
    			new BasicMobileShape(
    					new BasicShape(rotationDirection, -10.0, ShapeType.DIAMOND, 12.0, Color.MAGENTA), 
    					0.42, 
    					0.42
    					),
    			(3 * Math.PI) / 2,
    			0
    		);
    	countEnemies++;
    	
    	this.ableToShoot = false;
    	
    	nextEnemy = currentTime + 7000;
    	
    	if (countEnemies == 10) 
    		countEnemies = 0;
    	else
    		nextEnemy = currentTime + 100;
    }

	public void move(Long delta) 
	{
		double threshold = GameLib.HEIGHT * 0.30;
		
		double previousY = this.getY();
		
		this.basicEnemy.getMobileShape().move(
				this.getSpeedX() * Math.cos(this.getAngle()) * delta, 
				this.getSpeedY() * Math.sin(this.getAngle()) * delta * -1
			);
		this.basicEnemy.sumAngle(delta);
		
		if(previousY < threshold &&  this.getY() >= threshold) {
			if(this.getX() < GameLib.WIDTH / 2) this.setRotateSpeed(0.003);
			else this.setRotateSpeed(-0.003);
		}
		
		if(this.getRotateSpeed() > 0 && Math.abs(this.getAngle() - 3 * Math.PI) < 0.05){
			this.setRotateSpeed(0.0);
			this.basicEnemy.resetAngle();
			this.ableToShoot = true;
		}
		
		if(this.getRotateSpeed() < 0 && Math.abs(this.getAngle()) < 0.05){
			this.setRotateSpeed(0.0);
			this.basicEnemy.resetAngle();
			this.ableToShoot = true;
		}
	}
	
	public BasicEnemy getBasicEnemy() {
		return basicEnemy;
	}
	
	public static long getNextEnemy() {
		return nextEnemy;
	}
	
	@Override
	public boolean isAbleToShoot(Long currentTime) {
		return ableToShoot;
	}
	
	@Override
	public void refreshNextShoot(Long currentTime) {
		if (countShoots > 2) {
			countShoots = 0;
			this.ableToShoot = false;
		}
	}
	
	@Override
	public Projectile shoot() {
		double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
		Projectile p = new EnemyProjectile(
				this, 
				Math.cos(angles[countShoots] + Math.random() * Math.PI/6 - Math.PI/12) * 0.30, 
				Math.sin(angles[countShoots] + Math.random() * Math.PI/6 - Math.PI/12) * 0.30
			);
		
		countShoots++;
		p.spawn();
		
		return p;
	}

}