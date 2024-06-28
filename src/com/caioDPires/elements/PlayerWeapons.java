package com.caioDPires.elements;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.caioDPires.elements.explosion.ExplosionManager;
import com.caioDPires.gui.Sound;
import com.caioDPires.utils.Timer;
//Deveria ser playerBullets, mas
public class PlayerWeapons {

	private Timer timer;
	private ExplosionManager explosionManager;
	//O nome desse array (e tbm da classe machinegun) não representa o que eles são,
	//fui codando pensando em adicionar tipos diferentes de arma e quando fui tentar refatorar o eclipse buga
	//Esse array é de balas do jogador, o nome era pq daria pra adicionar tipos diferentes de arma
	public ArrayList<PlayerWeaponType> weapons = new ArrayList<PlayerWeaponType>();
	private Sound shootSound;
	
	public PlayerWeapons(){
		explosionManager = new ExplosionManager();
		timer = new Timer();
		shootSound = new Sound("/com/caioDPires/resources/shoot.wav");
	}
	
	public void draw(Graphics2D g){
		
		explosionManager.draw(g);
		for(int i = 0; i < weapons.size(); i++){
			weapons.get(i).draw(g);
		}
	}
	
	public void update(double delta, BasicBlocks blocks){
		
		explosionManager.update(delta);
		for(int i = 0; i < weapons.size(); i++){
			weapons.get(i).update(delta, blocks);
			if(weapons.get(i).destroy()) {
				ExplosionManager.createPixelExplosion(weapons.get(i).getxPos(), weapons.get(i).getyPos());
				weapons.remove(i);
			}
		}
	}
	//Quando o jogador aperta espaço, o engine chama esse metodo pra iniciar uma bala
	public void shootBullet(double xPos, double yPos, int width, int height){
		if(timer.timerEvent(250)) {
			if (shootSound.isPlaying()) {
				shootSound.stop();
			}
			shootSound.play();
			weapons.add(new MachineGun(xPos + 22, yPos + 15, width, height));//Adiciona a bala no array
		}
	}

	public void reset() {
		weapons.clear();
	}
}
