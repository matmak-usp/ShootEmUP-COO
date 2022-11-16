package entities;

import entities.enums.ShapeType;
import entities.enums.State;
import entities.interfaces.PowerUp;
import libs.GameLib;
import java.awt.Color;

public class NoColisionPowerUp implements PowerUp{
	
	private BasicMobileShape basicMobileShape;
	private long powerUpEnd;
	private long delay;

	private static long nextPowerUp;

	public NoColisionPowerUp(long delay, long currentTime) 
	{
		double speed = 0.10 + Math.random() * 0.005;
		double posX = Math.random() * (GameLib.WIDTH - 20.0) + 10.0;
		double posY = -10.0;

		this.basicMobileShape = new BasicMobileShape(
			new BasicShape(posX, posY, ShapeType.DIAMOND, 20.0, Color.YELLOW), 0.0, speed
		);
		
		nextPowerUp = currentTime + 15000;
		this.delay = delay;
		
		this.spawn();
	}

	public void resetState(Player player) {
		player.setState(State.ACTIVE);
	}

	public void toPowerUp(Player player, long currentTime) {
		this.setState(State.INACTIVE);
		setPowerUpEnd(currentTime);
		player.setState(State.INVENCIBLE);
	}

	public void setPowerUpEnd(long currentTime) { this.powerUpEnd = currentTime + getDelay(); }

	public BasicMobileShape getBasicMobileShape() { return this.basicMobileShape; }
	
	public long getDelay() { return this.delay;	}
	public long getPowerUpEnd() { return this.powerUpEnd; }

	public static long getNextPowerUp() {
        return nextPowerUp;
    }
}
