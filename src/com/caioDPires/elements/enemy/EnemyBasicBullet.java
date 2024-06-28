package com.caioDPires.elements.enemy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.caioDPires.elements.BasicBlocks;
import com.caioDPires.elements.Player;
import com.caioDPires.gui.Display;

public class EnemyBasicBullet extends EnemyWeaponType{

	private Rectangle bullet; // Representa a bala como um retângulo
	private double speed = 2.5d; // Velocidade da bala
	private int xPos, yPos; // Posições x e y da bala
	
	// Construtor da classe que inicializa a posição da bala
	public EnemyBasicBullet(double xPos, double yPos) {
		bullet = new Rectangle((int) xPos, (int) yPos, 5, 5); // Define a bala com largura e altura de 5 pixels
		setxPos((int) xPos); 
		setyPos((int) yPos); 
	}
	
	// Método para desenhar a bala na tela
	@Override
	public void draw(Graphics2D g) {
		if (bullet == null) {
			return; // Se a bala não existir, sai do método
		}
		//Define a cor da bala
		g.setColor(Color.RED); 
		g.fill(bullet); 
	}

	// Método para atualizar a posição da bala e verificar colisões
	@Override
	public void update(double delta, BasicBlocks blocks, Player player) {
		if (bullet == null) {
			return; // Se não existir, sai do método
		}
		
		setyPos((int) (getyPos() + (delta * speed))); //Bala desce de acordo com a velocidade e variacao de tempo entre frames
		bullet.y = getyPos();
		
		isOutofBounds(); // Verifica se a bala saiu da tela
		wallCollide(blocks); // Verifica colisões com as paredes
	}

	// Método para verificar colisão da bala com um retângulo
	@Override
	public boolean collision(Rectangle rect) {
		if (bullet != null && bullet.intersects(rect)) {
			return true; // Se a bala colide com o retângulo, retorna true
		}
		return false; // Caso contrário, retorna false
	}

	// Método para destruir a bala (não implementado)
	@Override
	public boolean destroy() {
		return false;
	}

	// Método para verificar colisões da bala com as paredes
	@Override
	protected void wallCollide(BasicBlocks blocks) {
		if (bullet == null) {
			return; // Se a bala não existir, sai do método
		}
		
		for (int w = 0; w < blocks.wall.size(); w++) {
			if(bullet.intersects(blocks.wall.get(w))) {
				blocks.wall.remove(w); // Remove a parede se houver colisão
				bullet = null; // Define a bala como nula após a colisão
				break;
			}
		}
	}

	// Método para verificar se a bala saiu dos limites da tela
	@Override
	protected void isOutofBounds() {
		if(bullet != null && bullet.y < 0 || bullet.y > Display.HEIGHT || bullet.x < 0 || bullet.x > Display.WIDTH){
			bullet = null; // Define a bala como nula se sair dos limites
		}
	}

	// Métodos getters e setters para a bala e suas posições
	public Rectangle getBullet() {
		return bullet;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
}

