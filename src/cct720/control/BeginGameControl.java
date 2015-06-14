package cct720.control;

import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import cct720.model.Bola;
import cct720.model.Cubo;
import cct720.model.MainUniverse;
import cct720.view.TheGame;

import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class BeginGameControl {

	private static final long serialVersionUID = 1L;
	private final int PWIDTH = 1000;
	private final int PHEIGHT = 600;
	private final float MOVE_STEP = 0.5f; 
	private final int TIME = 40;
	
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
				if (k.getKeyChar() == (KeyEvent.VK_SPACE)) {
					if (shootingBall != null) {
//						su.sceneBG.removeChild((su.sceneBG
//								.indexOfChild(shootingBall.getBranchGroup())));
						for (float i = 0.04f; i <= 1.0f || y >= 0.0f; i+=0.04f) {
							x = X0 + V0x*i;
							y = Y0 - (gravity*i*i)/2;
							shootingBall.getTg().getTransform(toMove);
							toMove.setTranslation(new Vector3d(x,y,z));
							shootingBall.getTg().setTransform(toMove);
							try {
//								System.out.println("x: " + x +
//										"\ny: " + y +
//										"\nz: " + z);
								Thread.sleep(40);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						shootingBall = null;
						theGame.updateQtdBolas(bolas.size());
					}
				} else if (k.getKeyChar() == 'r') {
					if (shootingBall == null && (b = bolas.poll()) != null) {
						su.sceneBG.addChild(b.getBranchGroup());
						shootingBall = b;
						theGame.updateQtdBolas(bolas.size());
					}
				} else if (k.getKeyChar() == 'j'){
					ViewingPlatform vp = su.su.getViewingPlatform();	
					TransformGroup steerTG = vp.getViewPlatformTransform();
					
					Transform3D t3d = new Transform3D();
					steerTG.getTransform(t3d);
					toMove = new Transform3D();
					toMove.setEuler(new Vector3d(0,1,0));
					t3d.mul(toMove);
					// args are: viewer posn, where looking, up direction
//					t3d.lookAt(su.getUserposn(), new Point3d(0, 0, 0),  new Vector3d(0, 1, 0));
//					t3d.invert();

					steerTG.setTransform(t3d);
				} else if (k.getKeyChar()=='a'){
					if (shootingBall != null){
						shootingBall.getTg().getTransform(t3d);
						toMove.setTranslation(new Vector3d(0,0,MOVE_STEP));
						t3d.mul(toMove);
						shootingBall.getTg().setTransform(t3d);
					}
				} else if (k.getKeyChar()=='w'){
					if (shootingBall != null){
						shootingBall.getTg().getTransform(t3d);
						toMove.setTranslation(new Vector3d(0,MOVE_STEP,0));
						t3d.mul(toMove);
						shootingBall.getTg().setTransform(t3d);
					}
				} else if (k.getKeyChar()=='d'){
					if (shootingBall != null){
						shootingBall.getTg().getTransform(t3d);
						toMove.setTranslation(new Vector3d(0,0,-MOVE_STEP));
						t3d.mul(toMove);
						shootingBall.getTg().setTransform(t3d);
					}
				} else if (k.getKeyChar()=='s'){
					if (shootingBall != null){
						shootingBall.getTg().getTransform(t3d);
						toMove.setTranslation(new Vector3d(0,-MOVE_STEP,0));
						t3d.mul(toMove);
						shootingBall.getTg().setTransform(t3d);
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
		
		// Iniciar um conjunto de 10 obstaculos
		Cubo cuboInicial = null;
		for (int i = 0; i < 10; i++) {
			cuboInicial = i%2==0?cuboControl.criarCuboAleatorio(true):cuboControl.criarCuboAleatorio(false);
			obstaculos.add(cuboInicial);
			su.sceneBG.addChild(cuboInicial.getBranchGroup());
		}
		
		// Iniciar um conjunto de 10 alvos
		for (int i = 0; i < 10; i++) {
			cuboInicial = i%2==0?cuboControl.gerarAlvos(true):cuboControl.gerarAlvos(false);
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
