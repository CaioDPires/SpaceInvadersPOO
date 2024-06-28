package com.caioDPires.elements;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.caioDPires.utils.Timer;

public class Sprite {
	//Esse array pega as diferentes imagens da animação do sprite
	private ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
	//Posição da imagem atual da animação
	private byte currentSprite;

	private boolean loop = false;//Se alguma animação do sprite é em loop
	private boolean play = false;//Se alguma animação do sprite roda uma vez só
	private boolean destoryAfterAnim = false;//Se é para o sprite ser destruído após o play

	private Timer timer;//Timer da animação
	private int animationSpeed;//Velocidade da animação
	private double xPos, yPos;
	private int width, height;
	private int limit;//Limite de imagens

	public Sprite(double xPos, double yPos, int rows, int columns, int animationSpeed, String imgPath) {
		this.animationSpeed = animationSpeed;
		this.xPos = xPos;
		this.yPos = yPos;
		
		try{
			//Carregar o spriteMap do atual sprite, dado as columns e rows do spriteMap onde suas imagens estão
			//O spriteMap contém as imagens de todos os inimigos, então apenas uma parte desta pra cada inimigo
			URL url = this.getClass().getResource(imgPath);
			BufferedImage pSprite = ImageIO.read(url);
			int spriteWidth = pSprite.getWidth() / columns;
			int spriteHeight = pSprite.getHeight() / rows;
			for(int y = 0; y < rows; y++) {
				for(int x = 0; x < columns; x++){
					addSprite(pSprite
							, 0 + (x * spriteWidth)
							, 0 + (y * spriteHeight)
							, spriteWidth
							, spriteHeight);
				}
			}
					
		//Se der erro carregando as imagens	
		}catch(IOException e){};
		//Inicia o timer
		timer = new Timer();
		//Ja que indexaçao começa por 0, limit = length do array - 1
		limit = sprites.size() - 1;
	}

	public void draw(Graphics2D g) {
		if (isSpriteAnimDestroyed())
			return;
		
		g.drawImage(sprites.get(currentSprite), (int) getxPos(), (int) getyPos(), width, height, null);
	}

	public void update(double delta) {
		if (isSpriteAnimDestroyed())
			return;

		if (loop && !play)
			loopAnimation();
		if (play && !loop)
			playAnimation();
	}
	//Pausa a animação
	public void stopAnimation() {
		loop = false;
		play = false;
	}
	//Reseta o sprite e pausa a animação
	public void resetSprite() {
		stopAnimation();
		currentSprite = 0;
	}
	//Pra animar em loop
	private void loopAnimation() {
		//Se tiver no ultimo frame da animaçao, volta pra primeira
		if (timer.isTimerReady(animationSpeed) && currentSprite == limit) {
			currentSprite = 0;//Volta pro inicial
			timer.resetTimer();//Reseta o timer
		}else if (timer.timerEvent(animationSpeed) && currentSprite != limit) {
			currentSprite++;//Avança a animação
		} 
	}
	//Pra animar uma vez
	private void playAnimation() {
		//Se a animaçao chegou ao fim mas nao eh pra destruir, so encerra
		if (timer.isTimerReady(animationSpeed) && currentSprite == limit && !isDestoryAfterAnim()) {
			play = false;//
			currentSprite = 0;
		} else if (timer.isTimerReady(animationSpeed) && currentSprite == limit && isDestoryAfterAnim()) {
			//Se chegou ao fim e é pra destruir, destroi
			sprites = null;
		}else if (timer.timerEvent(animationSpeed) && currentSprite != limit) {
			//Senao, avança
			currentSprite++;
		}
	}
	
	public byte getCurrentSprite() {
		return currentSprite;
	}

	public void setCurrentSprite(byte currentSprite) {
		this.currentSprite = currentSprite;
	}

	public boolean isLoop() {
		return loop;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public boolean isSpriteAnimDestroyed() {
		if (sprites == null)
			return true;

		return false;
	}

	public void addSprite(BufferedImage spriteMap, int xPos, int yPos,
			int width, int height) {
		sprites.add(spriteMap.getSubimage(xPos, yPos, width, height));
	}
	//Setar se o sprite tem animaçao unica e se eh pra destruir apos
	public void setPlay(boolean play, boolean destoryAfterAnim) {
		if(loop) {
			loop = false;
		}
		
		this.play = play;
		this.setDestoryAfterAnim(destoryAfterAnim);
	}

	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	public boolean isDestoryAfterAnim() {
		return destoryAfterAnim;
	}

	public void setDestoryAfterAnim(boolean destoryAfterAnim) {
		this.destoryAfterAnim = destoryAfterAnim;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getAnimationSpeed() {
		return animationSpeed;
	}

	public void setAnimationSpeed(int animationSpeed) {
		this.animationSpeed = animationSpeed;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		if(limit > 0) {
			this.limit = limit - 1;
		} else {
			this.limit = limit;
		}
	}
	//Reseta o limite pra quando precisar trocar de tipo de animaçao (loop, play)
	public void resetLimit() {
		limit = sprites.size() - 1;
	}
	
	public boolean isPlay() {
		return play;
	}
}
