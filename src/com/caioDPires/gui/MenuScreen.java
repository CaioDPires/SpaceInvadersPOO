package com.caioDPires.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.caioDPires.utils.StateMachine;
import com.caioDPires.utils.SuperStateMachine;
//Tela inicial (menu)
public class MenuScreen extends SuperStateMachine implements KeyListener {
	//Titulo e string (Press Enter)
	private Font tittleFont = new Font("Arial", Font.PLAIN, 64);
	private Font startFont = new Font("Arial", Font.PLAIN, 32);
	private String tittle = "Space Invaders";
	private String start = "Press Enter";
	
	public MenuScreen(StateMachine stateMachine) {
		super(stateMachine);
	}

	@Override
	//Porque as stateMachines tem que ter update, mas o menu nao precisa de atualizaçao em tempo real
	public void update(double delta) {
		
	}
	//So desenha uma vez mesmo
	@Override
	public void draw(Graphics2D g) {
		g.setFont(tittleFont);
		int tittleWidth = g.getFontMetrics().stringWidth(tittle);
		g.setColor(Color.yellow);
		g.drawString(tittle, ((Display.WIDTH/2)-(tittleWidth/2))-2, (Display.HEIGHT/2)-123);
		g.setColor(Color.green);
		g.drawString(tittle, (Display.WIDTH/2)-(tittleWidth/2), (Display.HEIGHT/2)-125);
		
		g.setFont(startFont);
		g.setColor(Color.white);
		int startWidth = g.getFontMetrics().stringWidth(start);
		g.drawString(start, (Display.WIDTH/2)-(startWidth/2), (Display.HEIGHT/2)+75);
	}

	@Override//KeyListener tem que ter
	public void init(Canvas canvas) {
		canvas.addKeyListener(this);
	}

	@Override//KeyListener tem que ter
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override//O único que é usado, pra quando Enter for soltado o jogo começar
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			getStateMachine().setState((byte) 1);
		}
	}

	@Override//KeyListener tem que ter
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
