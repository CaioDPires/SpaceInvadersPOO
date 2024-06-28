package com.caioDPires.elements.explosion;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class PixelExplosion implements ExplosionType {
    // xPosVol e yPosVol servem para randomizar a velocidade em que os pixels voam
    private double[] xPos, yPos, xPosVol, yPosVol, angle, energy;

    // Constrói uma explosão de pixels
    public PixelExplosion(double xPos, double yPos) {
        int index = 12; // Número de pixels na explosão
        this.xPos = new double[index];
        this.yPos = new double[index];
        this.xPosVol = new double[index];
        this.yPosVol = new double[index];
        this.angle = new double[index];
        this.energy = new double[index];

        for (int i = 0; i < index; i++) {
            this.xPos[i] = xPos; // Inicializa a posição x do pixel
            this.yPos[i] = yPos; // Inicializa a posição y do pixel

            this.xPosVol[i] = Math.random() * 1; // Velocidade aleatória no eixo x
            this.yPosVol[i] = Math.random() * 1; // Velocidade aleatória no eixo y
            this.energy[i] = Math.random(); // Energia aleatória do pixel

            Random r = new Random();
            angle[i] = r.nextInt(6) + 1; // Ângulo aleatório para a direção do pixel
        }
    }

    // Método para desenhar a explosão de pixels na tela
    @Override
    public void draw(Graphics2D g) {
        for (int i = 0; i < xPos.length; i++) {
            if (energy[i] >= 0.00d) {
                g.setColor(new Color(1.0f, 1.0f, 0f, (float) energy[i])); // Define a cor do pixel com base na energia
            } else {
                g.setColor(new Color(1.0f, 1.0f, 0f, 0)); // Define a cor do pixel como transparente se a energia for negativa
            }
            g.fillRect((int) xPos[i], (int) yPos[i], 3, 3); // Desenha o pixel como um pequeno retângulo
        }
    }

    // Método para atualizar a posição e a energia dos pixels
    @Override
    public void update(double delta) {
        for (int i = 0; i < xPos.length; i++) {
            energy[i] -= 0.01d; // Reduz a energia do pixel
            xPos[i] += xPosVol[i] * Math.cos(angle[i]); // Atualiza a posição x do pixel
            yPos[i] += yPosVol[i] * Math.cos(angle[i]); // Atualiza a posição y do pixel
        }
    }

    // Método para verificar se a explosão deve ser destruída
    @Override
    public boolean destroy() {
        int destroy = 0;
        for (int i = 0; i < xPos.length; i++) {
            if (energy[i] < 0.00d) {
                destroy++; // Conta quantos pixels têm energia negativa
            } else {
                break;
            }
        }

        if (destroy == energy.length) {
            return true; // Se todos os pixels têm energia negativa, a explosão deve ser destruída
        }

        return false; // Caso contrário, a explosão ainda não deve ser destruída
    }
}
