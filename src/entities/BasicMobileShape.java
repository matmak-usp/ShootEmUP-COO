package entities;

import entities.enums.State;
import entities.interfaces.MobileShape;
import libs.GameLib;

public class BasicMobileShape implements MobileShape {
	
	private BasicShape shape;
	
	private Double x;
	private Double y;
	
	private Double speedX;
	private Double speedY;
	private State state;
	
	public void move(Double moveX, Double moveY) {
		this.x += moveX;
		this.y += moveY;
	}
	
	public void inactivate() {
		this.state = State.INACTIVE;
	}
	
	public boolean isOnScreen() {
		if (this.getX() >= GameLib.WIDTH || this.getY() >= GameLib.HEIGHT) 
			return false;
		
		return true;
	}
	
	public boolean isActive() {
		return this.state == State.ACTIVE;
	}
	
	public BasicMobileShape(BasicShape shape, Double speedX, Double speedY) {
		this.x = shape.getX();
		this.y = shape.getY();
		this.shape = shape;
		this.speedX = speedX;
		this.speedY = speedY;
	}
	
	public void setSpeedX(Double speedX) {
		this.speedX = speedX;
	}
	public void setSpeedY(Double speedY) {
		this.speedY = speedY;
	}
	
	public Double getSpeedX() {
		return speedX;
	}
	public Double getSpeedY() {
		return speedY;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;  
	}
	public Double getX() {
		return x;
	}
	public Double getY() {
		return y;
	}
	public BasicShape getBasicShape() {
		return this.shape;
	}
	public BasicMobileShape getBasicMobileShape() {
		return this;
	}
}
