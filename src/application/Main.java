package application;

public class Main {
	
	public static void busyWait(long time) {	
		while(System.currentTimeMillis() < time) Thread.yield();
	}

	public static void main(String[] args) {
		
		boolean running = true;
		
		long delta;
		long currentTime = System.currentTimeMillis();
		
		Game game = new Game(currentTime);
		
		while (running) {
			delta = System.currentTimeMillis() - currentTime;
			currentTime = System.currentTimeMillis();
			
			game.drawBackground(delta);
			
			game.spawnEnemies(currentTime);
			game.shootEnemiesProjectiles(currentTime);

			game.adjustPlayerState(currentTime);
			
			game.drawObjects(currentTime, delta);
			
			game.verifyColisions(currentTime);

			game.adjustLists();
			
			game.verifyKeysPressed(currentTime, delta);
			
			game.refreshDisplay();
			
			busyWait(currentTime + 3);
		}
	}

}
