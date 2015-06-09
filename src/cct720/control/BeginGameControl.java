package cct720.control;

import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.media.j3d.Canvas3D;
import javax.vecmath.Vector3f;

import cct720.model.Bola;
import cct720.model.Cubo;
import cct720.model.MainUniverse;
import cct720.view.TheGame;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class BeginGameControl {

	private static final long serialVersionUID = 1L;
	private final int PWIDTH = 800;
	private final int PHEIGHT = 600;
	
	private Vector3f shootinPos = new Vector3f(10.0f, 5.0f, 0);
	private Vector3f wallPos = new Vector3f(-15.0f, -15.0f, 0);

	private TheGame theGame;
	private BolaControl bolaControl = new BolaControl();
	private CuboControl cuboControl = new CuboControl();
	private Queue<Bola> bolas = new LinkedList<Bola>();
	private Queue<Cubo> alvos = new LinkedList<Cubo>();
	private Bola shootingBall;
	private MainUniverse su;

	public BeginGameControl() {
		// Gerar Canvas3D
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();
		Canvas3D canvas3D = new Canvas3D(config);
		canvas3D.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent k) {
				Bola b;
				Cubo c;
				System.out.println("Key pressed: " + k.getKeyChar());
				if (k.getKeyChar() == (KeyEvent.VK_SPACE)) {
					if (shootingBall != null) {
						su.sceneBG.removeChild((su.sceneBG
								.indexOfChild(shootingBall.getBranchGroup())));
						shootingBall = null;
						theGame.updateQtdBolas(bolas.size());
					}
				} else if (k.getKeyChar() == 'r') {
					if (shootingBall == null && (b = bolas.poll()) != null) {
						su.sceneBG.addChild(b.getBranchGroup());
						shootingBall = b;
						theGame.updateQtdBolas(bolas.size());
					}
				}
			}
		});
		canvas3D.setFocusable(true);
		canvas3D.requestFocus();
		
		// Gerar UniversoPrincipal
		su = new MainUniverse(canvas3D);
		
		// Inicia um conjunto de 10 bolas para lançar
		Bola bolaInicial = null;
		for (int i = 0; i < 10; i++) {
			bolaInicial = bolaControl.criarBolaAleatoria(shootinPos);
			bolas.add(bolaInicial);
		}
		
		// Iniciar um conjunto de 10 alvos
		Cubo cuboInicial = null;
		for (int i = 0; i < 10; i++) {
			cuboInicial = i%2==0?cuboControl.criarCuboAleatorio(true):cuboControl.criarCuboAleatorio(false);
			alvos.add(cuboInicial);
			su.sceneBG.addChild(cuboInicial.getBranchGroup());
		}
		
		// Gera a parede limite do jogo
		su.sceneBG.addChild(cuboControl.gerarParede().getBranchGroup());

		// Inicializa a janela do jogo
		theGame = new TheGame(this, canvas3D, this.getPWIDTH(),
				this.getPHEIGHT());
		theGame.updateQtdBolas(bolas.size());
	}

	public int getPWIDTH() {
		return PWIDTH;
	}

	public int getPHEIGHT() {
		return PHEIGHT;
	}
}
