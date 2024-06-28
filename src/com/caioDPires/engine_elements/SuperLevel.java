package com.caioDPires.engine_elements;

import java.awt.Graphics2D;

import com.caioDPires.elements.BasicBlocks;
//Essa interface seria pra definir o que cada nivel tem que ter, como nao tive tempo ela é meio inutil, mas deixo no codigo
//caso queira expandir o jogo depois
public interface SuperLevel {

	void draw(Graphics2D g);
	void update(double delta, BasicBlocks blocks);
	void hasDirectionChange(double delta);
	void changeDirectionAllEnemys(double delta);
	
	boolean isGameOver();
	boolean isComplete();
	
	void destroy();
	void reset();
}
