package application;

import java.awt.Color;
import java.util.LinkedList;

import entities.Background;
import entities.BasicEnemy;
import entities.BasicMobileShape;
import entities.BasicShape;
import entities.CombinedEnemies;
import entities.EnemyProjectile;
import entities.NoColisionPowerUp;
import entities.Player;
import entities.PlayerProjectile;
import entities.ZigZagEnemy;
import entities.enums.ShapeType;
import entities.interfaces.Enemy;
import entities.interfaces.MobileShape;
import entities.interfaces.PowerUp;
import libs.GameLib;

public class Game {
	
	private Background background1;
	private Background background2;
	
	private Player player;
	LinkedList<PlayerProjectile> playerProjectiles;
	
	LinkedList<Enemy> enemies;
	LinkedList<EnemyProjectile> enemyProjectiles;
	
	LinkedList<PowerUp> powerUps;
	
	public Game(Long currentTime) {
		GameLib.initGraphics();
		
		background1 = new Background(0.07, 3, 3);
		background1.addStar(20);
		background2 = new Background(0.045, 2, 2);
		background2.addStar(50);
		
		createPlayer(currentTime);
		initLists();
	}
	
	private void createPlayer(Long currentTime) {
		BasicShape playerShape = new BasicShape(GameLib.WIDTH / 2.0, GameLib.HEIGHT * 0.90, ShapeType.PLAYER, 12.0, Color.BLUE);
		BasicMobileShape playerMobileShape = new BasicMobileShape(playerShape, 0.25, 0.25);
		player = new Player(playerMobileShape, currentTime);
	}
	
	private void initLists() {
		playerProjectiles = new LinkedList<PlayerProjectile>();
		enemyProjectiles = new LinkedList<EnemyProjectile>();

		enemies = new LinkedList<Enemy>();
		powerUps = new LinkedList<PowerUp>();
	}
	
	private boolean areColiding(MobileShape first, MobileShape second) {
		double dx = first.getX() - second.getX();
		double dy = first.getY() - second.getY();
		double dist = Math.sqrt(dx * dx + dy * dy);
		
		if(dist < (first.getRadius() + second.getRadius()) * 0.8)
			return true;
		
		return false;
	}
	
	public void drawBackground(Long delta) {
		background1.drawBackground(Color.GRAY, delta);
		background2.drawBackground(Color.DARK_GRAY, delta);
	}
	
	private void drawPowerUps(Long delta) 
	{
		for(PowerUp p : powerUps) {
			p.move(delta);
			p.draw();
		}
	}
	
	private void drawEnemies(Long currentTime, Long delta) {
		for(Enemy enemy : enemies)
		{				
			if (enemy.isExploding()) 
			{
				if (currentTime > enemy.getExplosionEnd()) {
					enemy.inactivate();
					continue;
				}

				enemy.drawExplosion(currentTime);
				continue;
			}
			
			enemy.move(delta);
			enemy.draw();
		}
	}
	
	private void drawProjectiles(Long delta) {
		for (PlayerProjectile p : playerProjectiles)
			p.move(delta);
		for (EnemyProjectile p : enemyProjectiles)
			p.move(delta);
	}
	
	private void drawPlayer(Long currentTime) {
		if (player.isExploding()) 
			player.drawExplosion(currentTime);
		else
			player.draw();
	}
	
	public void drawObjects(Long currentTime, Long delta) {
		drawPlayer(currentTime);
		drawPowerUps(delta);
		drawEnemies(currentTime, delta);
		drawProjectiles(delta);
	}
	
	public void adjustLists() {
		enemies.removeIf(e -> !e.isOnScreen() || (!e.isActive() && !e.isExploding()));
		powerUps.removeIf(e -> !e.isOnScreen() || (!e.isActive()));
		playerProjectiles.removeIf(p -> !p.isOnScreen() || !p.isActive());
		enemyProjectiles.removeIf(p -> !p.isOnScreen() || !p.isActive());
	}
	
	public void adjustPlayerState(Long currentTime) {
		
		if (player.isExploding() && currentTime > player.getExplosionEnd())
			player.spawn();
		
		if(player.isPoweredUp() && player.getPickedPowerUp().getPowerUpEnd() <= currentTime)
		{
			player.getPickedPowerUp().resetState(player);
			player.setPickedPowerUp(null);
			player.getBasicMobileShape().setColor(Color.BLUE);
		}
	}
	
	public void verifyKeysPressed(Long currentTime, Long delta) {
		if(GameLib.iskeyPressed(GameLib.KEY_UP)) player.moveUp(delta);
		if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) player.moveDown(delta);
		if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) player.moveLeft(delta);
		if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) player.moveRight(delta);
		
		if(GameLib.iskeyPressed(GameLib.KEY_CONTROL) && player.isAbleToShoot(currentTime)) 
		{
			playerProjectiles.add((PlayerProjectile) player.shoot());
			player.refreshNextShoot(currentTime);
		}
	}
	
	private void powerUpPlayer(PowerUp powerUp, Long currentTime) {
		powerUp.toPowerUp(player, currentTime);
		player.setPickedPowerUp(powerUp);
		player.getBasicMobileShape().setColor(Color.CYAN);
	}
	
	private void verifyPowerUpColision(Long currentTime) {
		for(PowerUp p : powerUps) {
			if (areColiding(p, player) && !player.isExploding())
				powerUpPlayer(p, currentTime);
		}
	}
	
	private void verifyPlayerColision(Long currentTime) {
		for (EnemyProjectile eP : enemyProjectiles) {
			if(areColiding(eP, player) && !player.isExploding()) {
				player.explode(currentTime, (long)2000);
				eP.inactivate();
			}
		}
	}
	
	private void verifyEnemyColision(Long currentTime) {
		for(Enemy enemy : enemies)
		{				
			if(areColiding(enemy, player) && !player.isExploding())
				player.explode(currentTime, (long) 2000);
			
			for(PlayerProjectile pp : playerProjectiles) 
			{
				if(areColiding(enemy, pp) && !enemy.isExploding()){
					enemy.explode(currentTime, (long) 500);
					pp.inactivate();
				}
			}
		}
	}
	
	public void verifyColisions(Long currentTime) {
		verifyPowerUpColision(currentTime);
		verifyEnemyColision(currentTime);
		verifyPlayerColision(currentTime);
	}
	
	public void spawnEnemies(Long currentTime) {
		if(currentTime > BasicEnemy.getNextEnemy())
		{
			BasicEnemy b = new BasicEnemy(currentTime);
			enemies.add(b);
		}

		if(currentTime > ZigZagEnemy.getNextEnemy())
		{
			ZigZagEnemy z = new ZigZagEnemy(currentTime);
			enemies.add(z);
		}
		
		if(currentTime > CombinedEnemies.getNextEnemy())
		{
			CombinedEnemies c = new CombinedEnemies(currentTime);
			enemies.add(c);
		}

		if(currentTime > NoColisionPowerUp.getNextPowerUp())
		{
			NoColisionPowerUp n = new NoColisionPowerUp(5000, currentTime);
			powerUps.add(n);
		}
	}
	
	public void shootEnemiesProjectiles(Long currentTime) {
		for (Enemy enemy : enemies) {
			if (enemy.isAbleToShoot(currentTime)) {
				enemyProjectiles.add((EnemyProjectile) enemy.shoot());
				enemy.refreshNextShoot(currentTime);
			}
		}
	}

	public void refreshDisplay() {
		GameLib.display();
	}
}
