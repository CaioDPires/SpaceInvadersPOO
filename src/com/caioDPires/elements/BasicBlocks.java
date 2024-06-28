package com.caioDPires.elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
//Classe que constroi as paredes do jogo
public class BasicBlocks {

    // Lista de retângulos que representam as paredes do jogo
    public ArrayList<Rectangle> wall = new ArrayList<Rectangle>();

    // Construtor da classe que cria os blocos básicos
    public BasicBlocks() {
        basicBlocks(75, 450);
        basicBlocks(275, 450);
        basicBlocks(475, 450);
        basicBlocks(675, 450);
    }

    // Método para desenhar os blocos na tela
    public void draw(Graphics2D g) {
        g.setColor(Color.GREEN); // Define a cor dos blocos como verde
        for (int i = 0; i < wall.size(); i++) {
            g.fill(wall.get(i)); // Desenha cada retângulo da lista de blocos
        }
    }

    // Método para criar os blocos básicos
    public void basicBlocks(int xPos, int yPos) {
        int wallWidth = 3; // Largura da parede
        int x = 0;
        int y = 0;

        for (int i = 0; i < 13; i++) {
            if ((14 + (i * 2) + wallWidth < 22 + wallWidth)) {
                row(14 + (i * 2) + wallWidth, xPos - (i * 3), yPos + (i * 3)); // Cria uma linha de blocos
                x = (i * 3) + 3;
            } else {
                row(22 + wallWidth, xPos - x, yPos + (i * 3)); // Cria uma linha de blocos
                y = (i * 3);
            }
        }

        // Lado esquerdo
        for (int i = 0; i < 5; i++) {
            row(8 + wallWidth - i, xPos - x, (yPos + y) + (i * 3));
        }

        // Lado direito
        for (int i = 0; i < 5; i++) {
            row(8 + wallWidth - i, (xPos - x + (14 * 3)) + (i * 3), (yPos + y) + (i * 3));
        }
    }

    // Método para criar uma linha de blocos
    public void row(int rows, int xPos, int yPos) {
        for (int i = 0; i < rows; i++) {
            Rectangle brick = new Rectangle(xPos + (i * 3), yPos, 3, 3); // Cria um bloco
            wall.add(brick); // Adiciona o bloco à lista de paredes
        }
    }

    // Método para resetar os blocos, limpando a lista e recriando os blocos básicos
    public void reset() {
        wall.clear(); // Limpa a lista de blocos

        basicBlocks(75, 450);
        basicBlocks(275, 450);
        basicBlocks(475, 450);
        basicBlocks(675, 450); // Recria os blocos básicos
    }
}
