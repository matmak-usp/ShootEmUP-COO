package entities;

import java.awt.Color;
import java.util.function.Function;

import entities.enums.ShapeType;
import entities.interfaces.Enemy;
import entities.interfaces.Projectile;

public class EnemyProjectile implements Projectile {
	
	private BasicMobileShape mobileShape;
	private boolean isSetNewMovimentationRule = false;
	private Function<EnemyProjectile, EnemyProjectile> newMovimentationRule;
	
	public EnemyProjectile(Enemy enemy, Double speedX, Double speedY) 
	{
		this.mobileShape = new BasicMobileShape(
				new BasicShape(enemy.getX(), enemy.getY(), ShapeType.CIRCLE, 2.0, Color.RED), speedX, speedY
		);
	}

	public BasicMobileShape getMobileShape() {
		return mobileShape;
	}
	
	@Override
	public void move(Long delta) {
		if (isSetNewMovimentationRule) 
			newMovimentationRule.apply(this);
		
		this.getMobileShape().move(this.getSpeedX() * delta, this.getSpeedY() * delta);
		this.draw();
	}
	
	public void setNewMovimentation(Function<EnemyProjectile, EnemyProjectile> newMovimentationRule) {
		isSetNewMovimentationRule = true;
		this.newMovimentationRule = newMovimentationRule;
	}
	
	public BasicMobileShape getBasicMobileShape() {
    	return mobileShape;
    }
}