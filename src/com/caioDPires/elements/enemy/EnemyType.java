package com.caioDPires.elements.enemy;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.caioDPires.elements.BasicBlocks;
import com.caioDPires.elements.Player;

import com.caioDPires.engine_elements.EnemyBulletHandler;

public abstract class EnemyType {
	
	private EnemyBulletHandler bulletHandler;
	
	public EnemyType(EnemyBulletHandler bulletHandler) {
		this.bulletHandler = bulletHandler;
	}

	public abstract void draw(Graphics2D g);
	public abstract void update(double delta, Player player, BasicBlocks blocks);
	public abstract void changeDirection(double delta);
	
	public abstract boolean deathScene();
	public abstract boolean collide(int i, Player player, BasicBlocks blocks, ArrayList<EnemyType> enemys);
	public abstract boolean isOutOfBounds();
	
	public EnemyBulletHandler getBulletHandler() {
		return bulletHandler;
	}
}
