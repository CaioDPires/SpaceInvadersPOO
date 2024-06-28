package com.caioDPires.engine_elements;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.caioDPires.elements.BasicBlocks;
import com.caioDPires.elements.Player;
import com.caioDPires.elements.enemy.EnemyWeaponType;
import com.caioDPires.elements.explosion.ExplosionManager;
//Handler das balas, basicamente adiciona a um array e verifica para todas as balas no array
//se uma delas esta colidindo com o jogador ou uma parede
public class EnemyBulletHandler {
	//O nome weaponTypes é porque inicialmente queria que houvessem tipos diferentes de armas do alien,
	//nao pude implementar pela falta de tempo e pq estou sozinho
	private List<EnemyWeaponType> weaponTypes = new ArrayList<>();
	
	public void addBullet(EnemyWeaponType weaponType) {
		this.weaponTypes.add(weaponType);
	}

	public void draw(Graphics2D g) {
		for (EnemyWeaponType enemyWeaponType : weaponTypes) {
			enemyWeaponType.draw(g);
		}
	}
	
	public void update(double delta, BasicBlocks blocks, Player player) {
		for (int i = 0; i < weaponTypes.size(); i++) {
			weaponTypes.get(i).update(delta, blocks, player);
			if (weaponTypes.get(i).collision(player.getRect())) {//Se colidir com o jogador:
				//A bala explode
				ExplosionManager.createPixelExplosion(weaponTypes.get(i).getxPos(), weaponTypes.get(i).getyPos());
				weaponTypes.remove(i);
				//E o jogador perde uma vida
				player.hit();
			}
		}
	}
	//
	public void reset() {
		weaponTypes.clear();
	}
	
}
