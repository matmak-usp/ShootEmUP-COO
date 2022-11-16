package entities;

import java.awt.Color;

import entities.enums.ShapeType;
import entities.interfaces.Projectile;

public class PlayerProjectile implements Projectile {
	
	private BasicMobileShape mobileShape;
	
	private Double speedX = 0.0;
	private Double speedY = -1.0;
	
	public PlayerProjectile(Double x, Double y) 
	{
		this.mobileShape = new BasicMobileShape(
				new BasicShape(x, y, ShapeType.LINE, 0.0, Color.GREEN), speedX, speedY
		);
	}

	public BasicMobileShape getBasicMobileShape() {
		return mobileShape;
	}

}
