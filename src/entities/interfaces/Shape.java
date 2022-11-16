package entities.interfaces;

import java.awt.Color;

import entities.enums.ShapeType;
import libs.GameLib;

public interface Shape {
	
	public ShapeType getShapeType();
	public Double getX();
	public Double getY();
	public Double getRadius();
	public Color getColor();
	
	default void draw(Double x, Double y) {
		GameLib.setColor(this.getColor());
		
		if(this.getShapeType() == ShapeType.CIRCLE)
			GameLib.drawCircle(this.getX(), this.getY(), this.getRadius());
		if(this.getShapeType() == ShapeType.LINE) {
			GameLib.drawLine(x, y - 5, x, y + 5);
			GameLib.drawLine(x - 1, y - 3, x - 1, y + 3);
			GameLib.drawLine(x + 1, y - 3, x + 1, y + 3);			
		}
		if(this.getShapeType() == ShapeType.DIAMOND)
			GameLib.drawDiamond(this.getX(), this.getY(), this.getRadius());
		if (this.getShapeType() == ShapeType.PLAYER)
			GameLib.drawPlayer(this.getX(), this.getY(), this.getRadius());
	}
}
