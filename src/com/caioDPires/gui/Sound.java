package com.caioDPires.gui;

import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class Sound implements LineListener {
	//Classe que implementa a funcionalidade de som do jogo
	//O clip q é pra ser rodado
	private Clip soundClip;
	
	public Sound(String path) {
		try {
			URL url = getClass().getResource(path);//Carrega o arquivo .wav
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);//Transforma em audioInputStream
			AudioFormat format = audioInputStream.getFormat();//Pega o formato
			DataLine.Info info = new Info(Clip.class, format);//E transforma em dataline
			soundClip = (Clip) AudioSystem.getLine(info);//Pra poder inicializar o soundClip
			soundClip.open(audioInputStream);
			soundClip.addLineListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(LineEvent event) {
		if (event.getType().equals(LineEvent.Type.STOP)) {
			soundClip.setFramePosition(1);
		}
	}
	//Inicia o som
	public void play() {
		soundClip.start();
	}
	//Roda em loop
	public void loop() {
		soundClip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	//Para o audio
	public void stop() {
		soundClip.stop();
		soundClip.setFramePosition(1);
	}
	//Verificar se esta rodando
	public boolean isPlaying() {
		return soundClip.isRunning();
	}
}
