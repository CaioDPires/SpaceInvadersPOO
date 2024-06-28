package com.caioDPires.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.caioDPires.utils.StateMachine;

public class Display extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;

    // Método principal que cria a janela do jogo
    public static void main(String[] args) {
        Display display = new Display();
        JFrame frame = new JFrame();
        frame.add(display);
        frame.pack();
        frame.setTitle("Space Invaders"); // Define o título da janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define a operação de fechamento
        frame.setResizable(false); // Impede que a janela seja redimensionada
        frame.setVisible(true); // Torna a janela visível
        display.start(); // Inicia o jogo
    }

    private boolean running = false; // Indica se o jogo está rodando
    private Thread thread; // Thread do jogo

    // Método sincronizado para iniciar o jogo
    public synchronized void start() {
        if (running)
            return;

        running = true;

        thread = new Thread(this); // Cria a thread do jogo
        thread.start(); // Inicia a thread
    }

    // Método sincronizado para parar o jogo
    public synchronized void stop() {
        if (!running)
            return;

        running = false;

        try {
            thread.join(); // Aguarda a finalização da thread
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int WIDTH = 800, HEIGHT = 600; // Largura e altura da tela do jogo
    public int FPS; // Frames por segundo

    public static StateMachine state; // Máquina de estados do jogo

    // Construtor da classe Display
    public Display() {
        this.setSize(WIDTH, HEIGHT); // Define o tamanho da tela
        this.setFocusable(true); // Define o foco da tela

        state = new StateMachine(this); // Inicializa a máquina de estados
        state.setState((byte) 0); // Define o estado inicial do jogo
    }

    // Método run que contém o loop principal do jogo
    @Override
    public void run() {
        long timer = System.currentTimeMillis();
        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60; // Define o FPS alvo
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS; // Tempo ótimo entre frames em nanosegundos
        int frames = 0;

        this.createBufferStrategy(3); // Cria uma estratégia de buffer triplo (2 seria bom pra jogo 2d, 3 acelera mais)
        BufferStrategy bs = this.getBufferStrategy();
        while (running) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double) OPTIMAL_TIME); // Calcula o delta time

            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                FPS = frames; // Atualiza o FPS
                frames = 0;
                System.out.println(FPS); // Imprime o FPS no console
            }

            draw(bs); // Desenha o frame
            update(delta); // Atualiza o estado do jogo

            try {
                Thread.sleep(((lastLoopTime - System.nanoTime()) + OPTIMAL_TIME) / 1000000); // Aguarda até o próximo frame
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Método para desenhar na tela (utilizando bufferStrategy, peguei de um site com tutoriais de 
    //game dev em Java, permite ter controle mais fino do rendering, tornando o jogo mais rápido
    public void draw(BufferStrategy bs) {
        do {
            do {
                Graphics2D g = (Graphics2D) bs.getDrawGraphics();
                g.setColor(Color.BLACK); // Define a cor de fundo como preta
                g.fillRect(0, 0, WIDTH + 50, HEIGHT + 50); // Preenche o fundo

                state.draw(g); // Desenha o estado atual do jogo

                g.dispose();
            } while (bs.contentsRestored());
            bs.show();
        } while (bs.contentsLost());
    }

    // Método para atualizar o estado do jogo
    public void update(double delta) {
        state.update(delta); // Atualiza o estado da máquina de estados
    }
}
