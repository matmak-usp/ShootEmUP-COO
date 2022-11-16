package entities.interfaces;

import java.awt.Color;

import entities.BasicMobileShape;
import entities.BasicShape;
import entities.enums.ShapeType;
import entities.enums.State;

public interface MobileShape extends Shape {
		
	public BasicMobileShape getBasicMobileShape();

	default BasicShape getBasicShape() {
		return this.getBasicMobileShape().getBasicShape();
	}
	
	default void draw() {
		this.draw(this.getX(), this.getY());
	}
	default void inactivate() {
		this.setState(State.INACTIVE);
	}
	default Color getColor() {
		return this.getBasicShape().getColor();
	}
	default ShapeType getShapeType() {
		return this.getBasicShape().getShapeType();
	}
	default Double getRadius() {
		return this.getBasicShape().getRadius();
	}

	default void setColor(Color color)
	{
		this.getBasicShape().SetColor(color);
	}
	
	default void setSpeedX(Double speedX) {
		this.getBasicMobileShape().setSpeedX(speedX);
	}
	
	default void setSpeedY(Double speedY) {
		this.getBasicMobileShape().setSpeedY(speedY);
	}
	
	default boolean isActive() {
		return this.getBasicMobileShape().isActive();
	}
	
	default State getState() {
		return this.getBasicMobileShape().getState();
	}
	
	default MobileShape spawn() {
		this.setState(State.ACTIVE);
		this.getBasicMobileShape().draw(this.getX(), this.getY());
		return this.getBasicMobileShape();
	}
	
	default boolean isOnScreen() {
		return this.getY() >= 0 && this.getBasicMobileShape().isOnScreen();
	}
	
	default void move(Long delta) {
		if(!this.isActive())
			return;
		
		this.getBasicMobileShape().move(this.getSpeedX() * delta, this.getSpeedY() * delta);
		this.draw();
	}
		
	default Double getSpeedX() {
		return this.getBasicMobileShape().getSpeedX();
	}
	
	default Double getSpeedY() {
		return this.getBasicMobileShape().getSpeedY();
	}
	
	default Double getX() {
		return this.getBasicMobileShape().getX();
	}
	
	default Double getY() {
		return this.getBasicMobileShape().getY();
	}
	
	default void setState(State state) {
		this.getBasicMobileShape().setState(state);
	}
	
}
