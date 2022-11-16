package entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import libs.GameLib;

public class Background {
	private double speed;
	private double width;
	private double height;
	private double count;


	private ArrayList<Double> x;
	private ArrayList<Double> y;


	public void SetCount(long delta)
	{
		this.count += getSpeed() * delta;
	}

	
	public Background (double speed, double width, double height){
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.count = 0;

		this.x = new ArrayList<Double>();
		this.y = new ArrayList<Double>();
	}
	
	public double getSpeed(){
		return this.speed;
	}
	
	public double getWidth() {
		return this.width;
	}
	
	public double getHeight() {
		return this.height;
	}
	
	public void addStar(int quantity){
		for(int i = 0; i < quantity; i++) {
			x.add(Math.random()*GameLib.WIDTH);
			y.add(Math.random()*GameLib.HEIGHT);
		}
	}
	
	public void drawBackground(Color color, long delta){
		GameLib.setColor(color);
		Iterator<Double> itX = this.x.iterator();
		Iterator<Double> itY = this.y.iterator();

		SetCount(delta);
		
		while(itX.hasNext()) {
			Double posx = itX.next();
			Double posy = itY.next();

			GameLib.fillRect(posx,( posy + this.count ) % GameLib.HEIGHT, getWidth(), getHeight());
		}
	}
}
