package cct720.control;

import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import cct720.model.Bola;
import cct720.model.Cubo;
import cct720.model.MainUniverse;
import cct720.view.TheGame;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class BeginGameControl {

	private static final long serialVersionUID = 1L;
	private final int PWIDTH = 1000;
	private final int PHEIGHT = 600;
	private final float MOVE_STEP = 0.5f;
	private final float TIME = 0.04f;

	private final float V0xInicial = -10.0f;
	private final float X0Inicial = 10.0f;
	private final float Y0Inicial = 5.0f;
	private final float gravityInicial = 10.0f;
	private final float xInicial = X0Inicial;
	private final float yInicial = Y0Inicial;
	private final float zInicial = 0.0f;

	private float V0x = -10.0f;
	private float X0 = 10.0f;
	private float Y0 = 5.0f;
	private float gravity = 10.0f;
	private float x = X0;
	private float y = Y0;
	private float z = 0.0f;

	private Transform3D toMove = new Transform3D();
	private Transform3D toRot = new Transform3D();
	private Transform3D t3d = new Transform3D();

	private Vector3f shootinPos = new Vector3f(X0, Y0, 0);
	private Vector3f wallPos = new Vector3f(-15.0f, -15.0f, 0);

	private TheGame theGame;
	private BolaControl bolaControl = new BolaControl();
	private CuboControl cuboControl = new CuboControl();
	private Queue<Bola> bolas = new LinkedList<Bola>();
	private Queue<Cubo> obstaculos = new LinkedList<Cubo>();
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
				/*** Teclas para alterar a o estado de lançamento ou recarga ****/
				if (k.getKeyChar() == (KeyEvent.VK_SPACE)) {
					if (shootingBall != null) {
						try {
							if(!bolaControl.moveBola(TIME, X0, Y0, x, y, z, V0x, gravity, shootingBall))
								bolaControl.deformBola(TIME, X0, Y0, x, y, z, V0x, gravity, shootingBall);
							// System.out.println("x: " + x +
							// "\ny: " + y +
							// "\nz: " + z);
							Thread.sleep(40);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						shootingBall = null;
						theGame.updateQtdBolas(bolas.size());
					}
				} else if (k.getKeyChar() == 'r') {
					if (shootingBall == null && (b = bolas.poll()) != null) {
						resetThrowPosition();
						su.sceneBG.addChild(b.getBranchGroup());
						shootingBall = b;
						theGame.updateQtdBolas(bolas.size());

					}
					/*** Teclas para alterar posição de lançamento da bola ****/
				} else if (k.getKeyChar() == 'a') {
					if (shootingBall != null) {
						shootingBall.getTg().getTransform(t3d);
						toMove.setTranslation(new Vector3d(0, 0, MOVE_STEP));
						t3d.mul(toMove);
						shootingBall.getTg().setTransform(t3d);
						z += MOVE_STEP;
					}
				} else if (k.getKeyChar() == 'w') {
					if (shootingBall != null) {
						shootingBall.getTg().getTransform(t3d);
						toMove.setTranslation(new Vector3d(0, MOVE_STEP, 0));
						t3d.mul(toMove);
						shootingBall.getTg().setTransform(t3d);
						Y0 += MOVE_STEP;
						y = Y0;
					}
				} else if (k.getKeyChar() == 'd') {
					if (shootingBall != null) {
						shootingBall.getTg().getTransform(t3d);
						toMove.setTranslation(new Vector3d(0, 0, -MOVE_STEP));
						t3d.mul(toMove);
						shootingBall.getTg().setTransform(t3d);
						z -= MOVE_STEP;
					}
				} else if (k.getKeyChar() == 's') {
					if (shootingBall != null) {
						shootingBall.getTg().getTransform(t3d);
						toMove.setTranslation(new Vector3d(0, -MOVE_STEP, 0));
						t3d.mul(toMove);
						shootingBall.getTg().setTransform(t3d);
						Y0 -= MOVE_STEP;
						y = Y0;
					}
					/*** Teclas para alterar a velocidade inicial de lançamento ****/
				} else if (k.getKeyChar() == 'z') {
					if(V0x >= 0.0f)
						V0x+=1.0f;
				} else if (k.getKeyChar() == 'x') {
					if(V0x <= -30.0f)
						V0x-=1.0f;
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

		// Iniciar um conjunto de 10 obstaculos
		Cubo cuboInicial = null;
		for (int i = 0; i < 10; i++) {
			cuboInicial = i % 2 == 0 ? cuboControl.criarCuboAleatorio(true)
					: cuboControl.criarCuboAleatorio(false);
			obstaculos.add(cuboInicial);
			su.sceneBG.addChild(cuboInicial.getBranchGroup());
		}

		// Iniciar um conjunto de 10 alvos
		for (int i = 0; i < 10; i++) {
			cuboInicial = i % 2 == 0 ? cuboControl.gerarAlvos(true)
					: cuboControl.gerarAlvos(false);
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

	public void resetThrowPosition() {
		V0x = V0xInicial;
		X0 = X0Inicial;
		Y0 = Y0Inicial;
		x = xInicial;
		y = yInicial;
		z = zInicial;
	}
}
