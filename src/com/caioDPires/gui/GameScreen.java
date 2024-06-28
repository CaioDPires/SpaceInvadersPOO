package com.caioDPires.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.caioDPires.elements.BasicBlocks;
import com.caioDPires.elements.Player;
import com.caioDPires.utils.StateMachine;
import com.caioDPires.utils.SuperStateMachine;
import com.caioDPires.utils.TickTimer;

import com.caioDPires.engine_elements.EnemyBulletHandler;
import com.caioDPires.engine_elements.Level1;

public class GameScreen extends SuperStateMachine {

    private Player player;
    private BasicBlocks blocks;
    private Level1 level;
    private EnemyBulletHandler bulletHandler;

    public static int SCORE = 0; // Variável estática para manter o placar do jogo

    private Font gameScreen = new Font("Arial", Font.PLAIN, 48); // Fonte para mensagens na tela do jogo
    private TickTimer gameOverTimer = new TickTimer(180); // Temporizador para o fim do jogo
    private TickTimer completeTimer = new TickTimer(180); // Temporizador para completar o nível

    // Construtor da classe GameScreen
    public GameScreen(StateMachine stateMachine) {
        super(stateMachine);
        blocks = new BasicBlocks(); // Inicializa os blocos básicos
        bulletHandler = new EnemyBulletHandler(); // Inicializa o manipulador de balas inimigas
        player = new Player(Display.WIDTH / 2 - 50, Display.HEIGHT - 75, 50, 50, blocks); // Inicializa o jogador
        level = new Level1(player, bulletHandler); // Inicializa o nível 1 do jogo
    }

    // Método de atualização
    @Override
    public void update(double delta) {
        player.update(delta); // Atualiza o jogador
        level.update(delta, blocks); // Atualiza o nível

        // Verifica se o jogo acabou
        if (level.isGameOver()) {
            gameOverTimer.tick(delta); // Atualiza o temporizador de fim de jogo
            if (gameOverTimer.isEventReady()) { // Verifica se o evento de fim de jogo está pronto
                level.reset(); // Reinicia o nível
                blocks.reset(); // Reinicia os blocos
                getStateMachine().setState((byte) 0); // Define o estado inicial
                SCORE = 0; // Reinicia o placar
            }
        }

        // Verifica se o nível está completo
        if (level.isComplete()) {
            completeTimer.tick(delta); // Atualiza o timer
            if (completeTimer.isEventReady()) { // Se o timer esta pronto:
                level.reset(); // Reinicia o nível
            }
        }
    }

    // Método de desenho
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.drawString("Score: " + SCORE, 5, 15); // Desenha o placar

        g.setColor(Color.red);
        g.drawString("Health: " + player.getHealth(), 5, 35); // Desenha a saúde do jogador

        blocks.draw(g); // Desenha os blocos
        player.draw(g); // Desenha o jogador
        level.draw(g); // Desenha o nível

        // Desenha a mensagem de fim de jogo
        if (level.isGameOver()) {
            g.setColor(Color.red);
            g.setFont(gameScreen);
            String gameOver = "GAME OVER!";
            int gameOverWidth = g.getFontMetrics().stringWidth(gameOver);
            g.drawString(gameOver, (Display.WIDTH / 2) - (gameOverWidth / 2), Display.HEIGHT / 2);
        }

        // Desenha a mensagem de fim de nível
        if (level.isComplete()) {
            g.setColor(Color.green);
            g.setFont(gameScreen);
            String complete = "LEVEL COMPLETE!";
            int completeWidth = g.getFontMetrics().stringWidth(complete);
            g.drawString(complete, (Display.WIDTH / 2) - (completeWidth / 2), Display.HEIGHT / 2);
        }
    }

    // Método de inicialização
    @Override
    public void init(Canvas canvas) {
        canvas.addKeyListener(player); // Adiciona o ouvinte de teclado para o jogador
    }
}

