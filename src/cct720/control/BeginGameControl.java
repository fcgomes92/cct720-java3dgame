package cct720.control;

import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Switch;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import cct720.model.Animacao;
import cct720.model.Bola;
import cct720.model.Cubo;
import cct720.model.MainUniverse;
import cct720.view.TheGame;

import com.sun.j3d.utils.geometry.Box;
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
	private int pontos = 0;

	private Transform3D toMove = new Transform3D();
	private Transform3D toRot = new Transform3D();
	private Transform3D t3d = new Transform3D();

	private Vector3f shootinPos = new Vector3f(X0, Y0, 0);
	private Vector3f wallPos = new Vector3f(-15.0f, -15.0f, 0);

	private TheGame theGame;
	private BolaControl bolaControl = new BolaControl();
	private CuboControl cuboControl = new CuboControl();
	private ConfigControl configControl = new ConfigControl();
	private ObstacleCollisionControl obstacleCollisionControl;
	private TargetCollisionControl targetCollisionControl;
	private Queue<Bola> bolas = new LinkedList<Bola>();
	private Queue<Cubo> obstaculos = new LinkedList<Cubo>();
	private Queue<Cubo> alvos = new LinkedList<Cubo>();
	private Bola shootingBall;
	private MainUniverse su;

	private Cubo cuboInicial = null;
	private Bola bolaInicial = null;

	private Canvas3D canvas3D;

	private BeginGameControl bgc;
	
	public boolean executarExplosao = false;

	// Carrega as imagens da explosão
	private Switch explSwitch;
	private float explSize = 5.0f;
	private Animacao expl1Shape, expl2Shape, expl3Shape, expl4Shape;
	private AnimacaoControl animacaoControl = new AnimacaoControl();
	private ImageComponent2D[] exploIms;
	private TransformGroup explTG;
	private BranchGroup explBG;

	public BeginGameControl() {

		// Inicializa as imagens de animação
		this.exploIms = this.animacaoControl.loadImages("imgs/explo/explo", 6);

		// Gerar Canvas3D
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();
		canvas3D = new Canvas3D(config);
		canvas3D.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent k) {
				Bola b;
				Cubo c;
				// System.out.println("Key pressed: " + k.getKeyChar());
				/*** Teclas para alterar a o estado de lançamento ou recarga ****/
				if (k.getKeyChar() == (KeyEvent.VK_SPACE)) {
					if (shootingBall != null) {
						try {
							if (!bolaControl.moveBola(TIME, X0, Y0, x, y, z,
									V0x, gravity, shootingBall, bgc))
								bolaControl.deformBola(TIME, X0, Y0, x, y, z,
										V0x, gravity, shootingBall);
							if (executarExplosao)
								showExplosion();
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
						theGame.updateVelocidade(Math.abs((int) V0x));

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
					if (V0x < 0.0f)
						V0x += 1.0f;
					theGame.updateVelocidade(Math.abs((int) V0x));
				} else if (k.getKeyChar() == 'x') {
					if (V0x > -30.0f)
						V0x -= 1.0f;
					theGame.updateVelocidade(Math.abs((int) V0x));
				}

			}
		});
		canvas3D.setFocusable(true);
		canvas3D.requestFocus();

		// Gerar UniversoPrincipal
		su = new MainUniverse(canvas3D);

		// Inicia um conjunto de 10 bolas para lançar
		this.iniciarBolas();

		// Iniciar um conjunto de 10 obstaculos
		this.iniciarObstaculos();

		// Iniciar um conjunto de 10 alvos
		this.iniciarAlvos();

		// Gera a parede limite do jogo
		su.sceneBG.addChild(cuboControl.gerarParede().getBranchGroup());

		// Configuração dos objetos da explosão
		explTG = new TransformGroup();
		explTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE); // will move
		explTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		// t3d.set(new Vector3f(0,0,0));
		// explTG.setTransform(t3d);

		expl1Shape = new Animacao(new Point3f(0, 1, 0), 10.0f, exploIms, 1);
		expl2Shape = new Animacao(new Point3f(0, 1, 0), 10.0f, exploIms, 2);
		expl3Shape = new Animacao(new Point3f(0, 1, 0), 10.0f, exploIms, 3);
		expl4Shape = new Animacao(new Point3f(0, 1, 0), 10.0f, exploIms, 4);
		explTG.addChild(expl1Shape);
		explTG.addChild(expl2Shape);
		explTG.addChild(expl3Shape);
		explTG.addChild(expl4Shape);

		// create switch for explosions
		explSwitch = new Switch();
		explSwitch.setCapability(Switch.ALLOW_SWITCH_WRITE);
		explSwitch.addChild(explTG);
		explSwitch.setWhichChild(Switch.CHILD_NONE); // invisible initially
		// explSwitch.setWhichChild(0); // make visible

		// branchGroup holding everything
		explBG = new BranchGroup();
		explBG.addChild(explSwitch);

		su.sceneBG.addChild(explBG);

		// Inicializa a janela do jogo
		theGame = new TheGame(this, canvas3D, this.getPWIDTH(),
				this.getPHEIGHT());
		theGame.updateQtdBolas(bolas.size());
		theGame.updateGravidade((int) this.gravity);
		theGame.updatePontos(this.pontos);
		theGame.updateVelocidade(Math.abs((int) this.V0x));
		this.bgc = this;
	}

	public void iniciarBolas() {
		bolas = new LinkedList<Bola>();
		for (int i = 0; i < 10; i++) {
			bolaInicial = bolaControl.criarBolaAleatoria(shootinPos);
			bolas.add(bolaInicial);
		}
	}

	public void iniciarObstaculos() {
		obstaculos = new LinkedList<Cubo>();
		for (int i = 0; i < 10; i++) {
			cuboInicial = i % 2 == 0 ? cuboControl.criarCuboAleatorio(true)
					: cuboControl.criarCuboAleatorio(false);
			obstaculos.add(cuboInicial);
			obstacleCollisionControl = new ObstacleCollisionControl(cuboInicial
					.getBox().getShape(Box.BACK), cuboInicial.getBox()
					.getBounds(), su, this);
			obstacleCollisionControl.setCapability(BranchGroup.ALLOW_DETACH);
			cuboInicial.getBranchGroup().addChild(obstacleCollisionControl);
			obstacleCollisionControl = new ObstacleCollisionControl(cuboInicial
					.getBox().getShape(Box.FRONT), cuboInicial.getBox()
					.getBounds(), su, this);
			obstacleCollisionControl.setCapability(BranchGroup.ALLOW_DETACH);
			cuboInicial.getBranchGroup().addChild(obstacleCollisionControl);
			obstacleCollisionControl = new ObstacleCollisionControl(cuboInicial
					.getBox().getShape(Box.TOP), cuboInicial.getBox()
					.getBounds(), su, this);
			obstacleCollisionControl.setCapability(BranchGroup.ALLOW_DETACH);
			cuboInicial.getBranchGroup().addChild(obstacleCollisionControl);
			obstacleCollisionControl = new ObstacleCollisionControl(cuboInicial
					.getBox().getShape(Box.BOTTOM), cuboInicial.getBox()
					.getBounds(), su, this);
			obstacleCollisionControl.setCapability(BranchGroup.ALLOW_DETACH);
			cuboInicial.getBranchGroup().addChild(obstacleCollisionControl);
			obstacleCollisionControl = new ObstacleCollisionControl(cuboInicial
					.getBox().getShape(Box.LEFT), cuboInicial.getBox()
					.getBounds(), su, this);
			obstacleCollisionControl.setCapability(BranchGroup.ALLOW_DETACH);
			cuboInicial.getBranchGroup().addChild(obstacleCollisionControl);
			obstacleCollisionControl = new ObstacleCollisionControl(cuboInicial
					.getBox().getShape(Box.RIGHT), cuboInicial.getBox()
					.getBounds(), su, this);
			obstacleCollisionControl.setCapability(BranchGroup.ALLOW_DETACH);
			cuboInicial.getBranchGroup().addChild(obstacleCollisionControl);
			su.sceneBG.addChild(cuboInicial.getBranchGroup());
		}
	}

	public void iniciarAlvos() {
		alvos = new LinkedList<Cubo>();
		for (int i = 0; i < 10; i++) {
			cuboInicial = i % 2 == 0 ? cuboControl.gerarAlvos(true)
					: cuboControl.gerarAlvos(false);
			alvos.add(cuboInicial);
			targetCollisionControl = new TargetCollisionControl(cuboInicial
					.getBox().getShape(Box.BACK), cuboInicial.getBox()
					.getBounds(), su, this, cuboInicial);
			targetCollisionControl.setCapability(BranchGroup.ALLOW_DETACH);
			cuboInicial.getBranchGroup().addChild(targetCollisionControl);
			targetCollisionControl = new TargetCollisionControl(cuboInicial
					.getBox().getShape(Box.FRONT), cuboInicial.getBox()
					.getBounds(), su, this, cuboInicial);
			targetCollisionControl.setCapability(BranchGroup.ALLOW_DETACH);
			cuboInicial.getBranchGroup().addChild(targetCollisionControl);
			targetCollisionControl = new TargetCollisionControl(cuboInicial
					.getBox().getShape(Box.TOP), cuboInicial.getBox()
					.getBounds(), su, this, cuboInicial);
			targetCollisionControl.setCapability(BranchGroup.ALLOW_DETACH);
			cuboInicial.getBranchGroup().addChild(targetCollisionControl);
			targetCollisionControl = new TargetCollisionControl(cuboInicial
					.getBox().getShape(Box.BOTTOM), cuboInicial.getBox()
					.getBounds(), su, this, cuboInicial);
			targetCollisionControl.setCapability(BranchGroup.ALLOW_DETACH);
			cuboInicial.getBranchGroup().addChild(targetCollisionControl);
			targetCollisionControl = new TargetCollisionControl(cuboInicial
					.getBox().getShape(Box.LEFT), cuboInicial.getBox()
					.getBounds(), su, this, cuboInicial);
			targetCollisionControl.setCapability(BranchGroup.ALLOW_DETACH);
			cuboInicial.getBranchGroup().addChild(targetCollisionControl);
			targetCollisionControl = new TargetCollisionControl(cuboInicial
					.getBox().getShape(Box.RIGHT), cuboInicial.getBox()
					.getBounds(), su, this, cuboInicial);
			targetCollisionControl.setCapability(BranchGroup.ALLOW_DETACH);
			cuboInicial.getBranchGroup().addChild(targetCollisionControl);
			su.sceneBG.addChild(cuboInicial.getBranchGroup());
		}
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

	public Bola getShootingBall() {
		return shootingBall;
	}

	public void setShootingBall(Bola shootingBall) {
		this.shootingBall = shootingBall;
	}

	public Animacao getExplosao() {
		return expl1Shape;
	}

	public void setExplosao(Animacao explosao) {
		this.expl1Shape = explosao;
	}

	public void showExplosion() {
		try {
//			System.out.println("BOOOOM!");
			try {
				 
		         // Open an audio input stream.
		         InputStream input = new FileInputStream("sounds/boom"+(Math.random()>0.5d?"1":"2")+".wav");
		         InputStream bufferedIn = new BufferedInputStream(input);
		         AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn);
		         Clip clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         clip.start();
		      } catch (UnsupportedAudioFileException e) {
		         e.printStackTrace();
		      } catch (IOException e) {
		         e.printStackTrace();
		      } catch (LineUnavailableException e) {
		         e.printStackTrace();
		      }
			executarExplosao = false;
			this.explSwitch.setWhichChild(0); // make visible
			for (int i = 0; i < this.exploIms.length; i++) {
				try {
					expl1Shape.getAppearance().getTexture().setImage(0, exploIms[i]);
					expl2Shape.getAppearance().getTexture().setImage(0, exploIms[i]);
					expl3Shape.getAppearance().getTexture().setImage(0, exploIms[i]);
					expl4Shape.getAppearance().getTexture().setImage(0, exploIms[i]);
					Thread.sleep(100); // wait a while
				} catch (Exception ex) {
				}
			}
//			this.expl1Shape.showSeries(); // boom!
//			this.expl2Shape.showSeries(); // boom!
//			this.expl3Shape.showSeries(); // boom!
			this.explSwitch.setWhichChild(Switch.CHILD_NONE); // make invisible
		} catch (Exception ex) {
		}
	}

	public void btRestart() {
		this.pontos = 0;
		this.gravity = 10.0f;
		for (Cubo cubo : alvos) {
			su.sceneBG.removeChild(cubo.getBranchGroup());
		}
		for (Cubo cubo : obstaculos) {
			su.sceneBG.removeChild(cubo.getBranchGroup());
		}
		for (Bola bola : bolas) {
			su.sceneBG.removeChild(bola.getBranchGroup());
		}
		if (shootingBall != null)
			su.sceneBG.removeChild(shootingBall.getBranchGroup());

		this.resetThrowPosition();
		this.iniciarAlvos();
		this.iniciarObstaculos();
		this.iniciarBolas();
		this.theGame.updateQtdBolas(bolas.size());
		this.theGame.updatePontos(this.pontos);
		this.theGame.updateGravidade(this.gravity);
		this.canvas3D.requestFocus();
	}

	public void ganhaPto(int qtd) {
		this.pontos += qtd;
		this.theGame.updatePontos(this.pontos);
	}

	public void alterarGravidade(float g) {
		this.gravity = g;
		this.theGame.updateGravidade(this.gravity);
	}

	public void btConfig() {
		this.configControl.showConfigPanel(this);
		this.canvas3D.requestFocus();
	}

	public void btExit() {
		System.exit(0);
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public TheGame getTheGame() {
		return theGame;
	}

	public void setTheGame(TheGame theGame) {
		this.theGame = theGame;
	}

	public void updatePosExpl(Transform3D t) {
		this.explTG.setTransform(t);
	}
}
