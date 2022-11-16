package entities;

import java.awt.Color;

import entities.enums.ShapeType;
import entities.interfaces.Shape;

public class BasicShape implements Shape{
	
	private Double x;
	private Double y;
	private ShapeType shapeType;
	private Double radius;
	private Color color;	
	
	public BasicShape(Double x, Double y, ShapeType shapeType, Double radius, Color color) {
		this.x = x;
		this.y = y;
		this.shapeType = shapeType;
		this.radius = radius;
		this.color = color;
	}

	public Double getX() {
		return x;
	}

	public Double getY() {
		return y;
	}

	public ShapeType getShapeType() {
		return shapeType;
	}

	public Double getRadius() {
		return radius;
	}

	public Color getColor() {
		return color;
	}


	public void SetColor(Color color)
	{
		this.color = color;
	}

}
