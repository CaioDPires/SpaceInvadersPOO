package com.caioDPires.elements.enemy;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import com.caioDPires.elements.BasicBlocks;
import com.caioDPires.elements.Player;
import com.caioDPires.elements.Sprite;
import com.caioDPires.gui.Display;
import com.caioDPires.gui.GameScreen;
import com.caioDPires.gui.Sound;
import com.caioDPires.utils.Timer;

import com.caioDPires.engine_elements.EnemyBulletHandler;

public class EnemyTypeBasic extends EnemyType{
	//Velocidade do inimigo, double pra precisao qd multiplicar por delta
	private double speed = 1.0d; 
	
	private Rectangle rect;
	private Sprite enemySprite;
	
	private int shootTime;
	private Timer shootTimer;
	
	private Sound explosionSound;
	//Construtor q define a posicao, tamanho e da o handler de suas balas (rows e columns sao para carregar o spriteMap) 
	public EnemyTypeBasic(double xPos, double yPos, int rows, int columns, EnemyBulletHandler bulletHandler){
		super(bulletHandler);
		
		enemySprite = new Sprite(xPos, yPos, rows, columns, 300, "/com/caioDPires/resources/Invaders.png");
		enemySprite.setWidth(25);
		enemySprite.setHeight(25);
		//Limit é quantos frames a animação tem
		enemySprite.setLimit(2);
		//Define o retangulo do sprite, para checar outOfBounds ou colisao
		this.setRect(new Rectangle((int) enemySprite.getxPos(), (int) enemySprite.getyPos(), enemySprite.getWidth(), enemySprite.getHeight()));
		enemySprite.setLoop(true);
		//Timer pra definir de quanto em quanto tempo o inimigo atiraa
		shootTimer = new Timer();
		shootTime = new Random().nextInt(12000);
		
		explosionSound = new Sound("/com/caioDPires/resources/explosion.wav");
	}
	
	@Override
	public void draw(Graphics2D g) {
		enemySprite.draw(g);
	}

	@Override
	public void update(double delta, Player player, BasicBlocks blocks) {
		//Atualiza o sprite na tela
		enemySprite.update(delta);
		//Move lado a lado
		enemySprite.setxPos(enemySprite.getxPos() - (delta * speed));
		//Atualiza o retangulo para seguir a posiçao
		this.getRect().x = (int) enemySprite.getxPos();
		//Se ja deu a hora de atirar, atira
		if (shootTimer.timerEvent(shootTime)) {
			getBulletHandler().addBullet(new EnemyBasicBullet(getRect().x, getRect().y));
			shootTime = new Random().nextInt(12000);
		}
	}
	
	//Se os sprites chegarem no fim da tela, muda de direção e acelera
	@Override
	public void changeDirection(double delta) {
		speed *= -1.15d;
		enemySprite.setxPos(enemySprite.getxPos() - (delta * speed));
		this.getRect().x = (int) enemySprite.getxPos();
		
		enemySprite.setyPos(enemySprite.getyPos() + (delta * 15));
		this.getRect().y = (int) enemySprite.getyPos();
	}
	//Animaçao e som pra quando o inimigo tomar tiro
	@Override
	public boolean deathScene() {
		//Se ele nao tiver definido animação de explodir, encerra
		if(!enemySprite.isPlay())
			return false;
		//Explode o sprite
		if(enemySprite.isSpriteAnimDestroyed()) {
			if (!explosionSound.isPlaying()) {
				explosionSound.play();
			}
			return true;
		}
		
		return false;
	}
	//Quando houver colisao (com o player ou sua bala, destroi o sprite e chama deathscene pra tocar o som)
	@Override
	public boolean collide(int i, Player player, BasicBlocks blocks, ArrayList<EnemyType> enemys) {
		if(enemySprite.isPlay()) {
			if(enemys.get(i).deathScene()) {
				enemys.remove(i);
			}
			return false;
		}
		//verifica pra todas as balas do jogador presentes na tela, qual delas colidiu com o player
		for(int w = 0; w < player.playerWeapons.weapons.size(); w++) {
			if(enemys != null && player.playerWeapons.weapons.get(w).collisionRect(((EnemyTypeBasic) enemys.get(i)).getRect())) {
				enemySprite.resetLimit();
				enemySprite.setAnimationSpeed(120);
				enemySprite.setPlay(true, true);
				GameScreen.SCORE += 8;
				return true;
			}
		}
		
		return false;
	}

	@Override
	//Se o inimigo sair da tela
	public boolean isOutOfBounds() {
		if(rect.x > 0 && rect.x < Display.WIDTH - rect.width)
			return false;
		return true;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

}
