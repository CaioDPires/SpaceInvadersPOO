package com.caioDPires.elements.enemy;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.caioDPires.elements.BasicBlocks;
import com.caioDPires.elements.Player;

public abstract class EnemyWeaponType {
	public abstract void draw(Graphics2D g);
	public abstract void update(double delta, BasicBlocks blocks, Player player);
	
	public abstract boolean collision(Rectangle rect);
	public abstract boolean destroy();
	
	protected abstract void wallCollide(BasicBlocks blocks);
	protected abstract void isOutofBounds();
	
	public abstract int getxPos();
	public abstract int getyPos();
}
