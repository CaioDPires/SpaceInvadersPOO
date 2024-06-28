package com.caioDPires.elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;

import com.caioDPires.gui.Display;
//Arma do jogador
public class MachineGun extends PlayerWeaponType {

    private Rectangle bullet; // Representa a bala como um retângulo
    private final double speed = 2.5d; // Velocidade da bala

    // Construtor da classe que inicializa a posição e o tamanho da bala
    public MachineGun(double xPos, double yPos, int width, int height) {
        this.setxPos(xPos);
        this.setyPos(yPos);
        this.setWidth(width);
        this.setHeight(height);

        this.bullet = new Rectangle((int) getxPos(), (int) getyPos(), getWidth(), getHeight());
    }

    // Método para desenhar a bala na tela
    @Override
    public void draw(Graphics2D g) {
        if (bullet == null)
            return;

        g.setColor(Color.GREEN); // Define a cor da bala como verde
        g.fill(bullet); // Desenha a bala como um retângulo preenchido
    }

    // Método para atualizar a posição da bala e verificar colisões
    @Override
    public void update(double delta, BasicBlocks blocks) {
        if (bullet == null)
            return;

        this.setyPos(getyPos() - (delta * speed)); // Atualiza a posição y da bala de acordo com a velocidade e o delta
        bullet.y = (int) this.getyPos();
        wallCollide(blocks); // Verifica colisões com as paredes
        isOutofBounds(); // Verifica se a bala saiu da tela
    }

    // Método para verificar colisão da bala com um retângulo
    @Override
    public boolean collisionRect(Rectangle rect) {
        if (this.bullet == null)
            return false;

        if (bullet.intersects(rect)) {
            this.bullet = null; // Define a bala como nula após a colisão
            return true;
        }

        return false;
    }

    // Método para verificar colisão da bala com um polígono (não implementado)
    @Override
    public boolean collisionPoly(Polygon poly) {
        // TODO Auto-generated method stub
        return false;
    }

    // Método para verificar se a bala deve ser destruída
    @Override
    public boolean destroy() {
        if (bullet == null)
            return true;

        return false;
    }

    // Método para verificar colisões da bala com as paredes
    @Override
    protected void wallCollide(BasicBlocks blocks) {
        for (int i = 0; i < blocks.wall.size(); i++) {
            if (bullet.intersects(blocks.wall.get(i))) {
                blocks.wall.remove(i); // Remove a parede se houver colisão
                bullet = null; // Define a bala como nula após a colisão
                return;
            }
        }
    }

    // Método para verificar se a bala saiu dos limites da tela
    @Override
    protected void isOutofBounds() {
        if (this.bullet == null)
            return;

        if (bullet.y < 0 || bullet.y > Display.HEIGHT || bullet.x < 0 || bullet.x > Display.WIDTH) {
            bullet = null; // Define a bala como nula se sair dos limites
        }
    }
}

