package com.caioDPires.engine_elements;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.caioDPires.elements.BasicBlocks;
import com.caioDPires.elements.Player;
import com.caioDPires.elements.enemy.EnemyType;
import com.caioDPires.elements.enemy.EnemyTypeBasic;
import com.caioDPires.gui.Sound;
//Defini apenas um level pela falta de tempo, mas a estrutura era pra poder definir vários
//(nao consegui implementar geração automática de niveis sem que o jogo ficasse impossivel
//apos alguns)
public class Level1 implements SuperLevel{

	private Player player;
	private ArrayList<EnemyType> enemies = new ArrayList<EnemyType>();
	private EnemyBulletHandler bulletHandler;
	
	private Sound beep, boop;
	private boolean beepboop;
	//Constroi o nivel adicionando o jogador, as balas e o handler de balas
	public Level1(Player player, EnemyBulletHandler bulletHandler){
		this.player = player;
		this.bulletHandler = bulletHandler;
		addEnemies();
		//Beep e boop sao os sons dos alienigenas mudando de direçao
		beep = new Sound("/com/caioDPires/resources/beep.wav");
		boop = new Sound("/com/caioDPires/resources/boop.wav");
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(enemies == null)
			return;
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}
		bulletHandler.draw(g);
	}

	@Override
	public void update(double delta, BasicBlocks blocks) {
		if(enemies == null)
			return;
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).update(delta, player, blocks);
		}
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).collide(i, player, blocks, enemies);
		}
		hasDirectionChange(delta);
		bulletHandler.update(delta, blocks, player);
	}
	//Se algum inimigo esta no limite da tela, troca eles todos de direçao
	@Override
	public void hasDirectionChange(double delta) {
		if(enemies == null)
			return;
		
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).isOutOfBounds()){
				changeDirectionAllEnemys(delta);
			}
		}
	}
	//Faz a mudança de direçao
	@Override
	public void changeDirectionAllEnemys(double delta) {
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).changeDirection(delta);
		}
		if (beepboop) {
			beepboop = false;
			boop.play();
		} else {
			beepboop = true;
			beep.play();
		}
	}

	@Override
	public boolean isGameOver() {
		return player.getHealth() <= 0;
	}

	@Override
	public void destroy() {
		
	}

	@Override
	//Reseta o level (usaria caso o jogador perdesse e quisesse continuar, agora uso pra jogar o level 1 repetidamente)
	//Tirei o reset das paredes pra pelo menos ter alguma mudança entre niveis
	public void reset() {
		player.reset();
		enemies.clear();
		addEnemies();
		
		bulletHandler.reset();
	}
	
	public void addEnemies() {
		for(int y = 0; y < 5; y++){
			for(int x = 0; x < 10; x++){
				EnemyType e = new EnemyTypeBasic(150 + (x * 40), 25 + (y * 40), 1 , 3, bulletHandler);
				enemies.add(e);
			}
		}
	}
	//Acabou o level? true
	@Override
	public boolean isComplete() {
		return enemies.isEmpty();
	}
}
