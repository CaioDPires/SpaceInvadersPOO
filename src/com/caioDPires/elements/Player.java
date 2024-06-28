package com.caioDPires.elements;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.caioDPires.gui.Display;

public class Player implements KeyListener {
	
    private final double speed = 5.0d; // Velocidade do jogador
    private int health; // Vida do jogador

    private BufferedImage pSprite; // Sprite do jogador
    private Rectangle rect; // Retângulo para colisões
    private double xPos, yPos, startXPos, startYPos; // Posições do jogador
    private int width, height; // Largura e altura do jogador
    private BasicBlocks blocks; // Blocos básicos no jogo

    private boolean left = false, right = false, shoot = false; // Controle de movimentos e tiro

    public PlayerWeapons playerWeapons; // Armas do jogador

    // Construtor da classe que inicializa a posição, tamanho, vida e blocos do jogador
    public Player(double xPos, double yPos, int width, int height, BasicBlocks blocks) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.startXPos = xPos;
        this.startYPos = yPos;
        this.width = width;
        this.height = height;
        this.health = 3;

        rect = new Rectangle((int) xPos, (int) yPos + 25, width, height - 25); // Inicializa o retângulo de colisão

        try {
            URL url = this.getClass().getResource("/com/caioDPires/resources/Player.png");
            pSprite = ImageIO.read(url); // Carrega a sprite do jogador
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.blocks = blocks;
        playerWeapons = new PlayerWeapons(); // Inicializa as armas do jogador
    }

    // Método para desenhar o jogador na tela
    public void draw(Graphics2D g) {
        g.drawImage(pSprite, (int) xPos, (int) yPos, width, height, null); // Desenha a sprite do jogador
        playerWeapons.draw(g); // Desenha as armas do jogador
    }

    // Método para atualizar a posição do jogador e verificar ações
    public void update(double delta) {
        if (right && !left && xPos < Display.WIDTH - width) {
            xPos += speed * delta; // Move para a direita
            rect.x = (int) xPos; // Atualiza a posição do retângulo de colisão
        } 
        if (!right && left && xPos > 10) {
            xPos -= speed * delta; // Move para a esquerda
            rect.x = (int) xPos; // Atualiza a posição do retângulo de colisão
        }

        playerWeapons.update(delta, blocks); // Atualiza as armas do jogador

        if (shoot) {
            playerWeapons.shootBullet(xPos, yPos, 5, 5); // Atira uma bala
        }
    }

    // Método para detectar teclas pressionadas
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            right = true; // Move para a direita
        } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            left = true; // Move para a esquerda
        }

        if (key == KeyEvent.VK_SPACE) {
            shoot = true; // Atira
        }
    }

    // Método para detectar teclas soltas
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            right = false; // Para de mover para a direita
        } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            left = false; // Para de mover para a esquerda
        }

        if (key == KeyEvent.VK_SPACE) {
            shoot = false; // Para de atirar
        }
    }

    // Método não implementado para teclas digitadas
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    // Método para reduzir a vida do jogador quando atingido
    public void hit() {
        setHealth(getHealth() - 1);
    }

    // Método para obter a vida do jogador
    public int getHealth() {
        return health;
    }

    // Método para definir a vida do jogador
    public void setHealth(int health) {
        this.health = health;
    }

    // Método para obter o retângulo de colisão do jogador
    public Rectangle getRect() {
        return rect;
    }

    // Método para resetar o jogador às condições iniciais
    public void reset() {
        health = 3;
        left = false;
        right = false;
        shoot = false;

        xPos = startXPos;
        yPos = startYPos;
        rect.x = (int) xPos;
        rect.y = (int) yPos + 25;
        playerWeapons.reset(); // Reseta as armas do jogador
    }
}

