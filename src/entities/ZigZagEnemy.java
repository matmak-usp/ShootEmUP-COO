package entities;
import java.awt.Color;
import java.util.function.Function;

import entities.enums.ShapeType;
import entities.interfaces.Enemy;
import entities.interfaces.Projectile;
import libs.GameLib;

public class ZigZagEnemy implements Enemy {

    private BasicEnemy basicEnemy;

    private static long nextEnemy;
    private int direction;
    
    @Override
    public Projectile shoot() {
    	EnemyProjectile p = new EnemyProjectile(basicEnemy, this.getSpeedX() * 1.5 * direction, this.getSpeedY() * 1.5);
    	
    	Function<EnemyProjectile, EnemyProjectile> newMovimentationRule = new Function<EnemyProjectile, EnemyProjectile>() {
			@Override
			public EnemyProjectile apply(EnemyProjectile t) {
				if (t.getX() < 3 || t.getX() > GameLib.WIDTH - 3) {
					t.setSpeedX(t.getSpeedX() * -1);
				}
				return t;
			}
		};
    	
    	p.setNewMovimentation(newMovimentationRule);
    	p.spawn();
    	return p;
    }

    public ZigZagEnemy(Long currentTime) {
    	this.basicEnemy = new BasicEnemy(
    			new BasicMobileShape(
    					new BasicShape(Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0, ShapeType.DIAMOND, 12.0, Color.ORANGE), 
    					0.20 + Math.random() * 0.15, 
    					0.20 + Math.random() * 0.15
    					),
    			(3 * Math.PI) / 2,
    			(long) (200 + Math.random() * 500)
    		);
        
        nextEnemy = currentTime + 3000;
        this.direction = -1;
    }

    public void move(Long delta) 
    {    	
        if(this.getX() <= 20 || this.getX() >= GameLib.WIDTH - 20)
            direction *= -1;

    	this.basicEnemy.getMobileShape().move(this.getSpeedX() * direction * delta, delta * this.getSpeedY());
    }

	public BasicEnemy getBasicEnemy() {
		return basicEnemy;
	}
	
	public static long getNextEnemy() {
        return nextEnemy;
    }
}
