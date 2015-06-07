package teste.bola;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GraphicsConfiguration;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.Timer;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class BouncyBall extends Applet implements ActionListener, KeyListener{
	
	private Button startBtn = new Button("Start!");
	
	private TransformGroup objTrans;
	private Transform3D transform = new Transform3D();
	
	private float height = 0.0f;
	private float sign = 1.0f;
	private float xLoc = 0.0f;
	
	private Transform3D toMove = new Transform3D();
	
	private Timer timer;
	
	private Vector3d posInicial = new Vector3d(0,0,0);
	
	private float puxada = 0.0f;
	
	private boolean aimed = false;
	private float maxDistance = 1.0f;
	private float altura = 0.0f;
	private float step = 1;
	private long sleep = 50;
	
	public BouncyBall(){
		setLayout(new BorderLayout());
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		
		Canvas3D canvas = new Canvas3D(config);
		
		add("Center",canvas);
		canvas.addKeyListener(this);
		
		timer = new Timer(50,this);
		
		Panel panel = new Panel();
		panel.add(startBtn);
		add("North",panel);
		
		startBtn.addActionListener(this);
		startBtn.addKeyListener(this);
		
		
		
		SimpleUniverse universe = new SimpleUniverse(canvas);
		BranchGroup group = createSceneGraph();
		
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(group);
		
	}

	public BranchGroup createSceneGraph(){
		BranchGroup root = new BranchGroup();
		objTrans = new TransformGroup();
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		root.addChild(objTrans);
		
		objTrans = new TransformGroup();
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Sphere sphere = new Sphere(0.25f);
		
		Transform3D pos1 = new Transform3D();
		pos1.setTranslation(this.posInicial);
		
		objTrans.setTransform(pos1);
		objTrans.addChild(sphere);
		root.addChild(objTrans);
		
		// Definições da luz direcional
		BoundingSphere bounds = new BoundingSphere(new Point3d(0,0,0), 100);
		Color3f light1Color = new Color3f(3f,1f,1f);
		Vector3f light1Direction = new Vector3f(0f,0f,-12f);
		DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
		light1.setInfluencingBounds(bounds);
		
		Vector3f light1Direction2 = new Vector3f(0f,0f,12f);
		DirectionalLight light2 = new DirectionalLight(light1Color, light1Direction2);
		light2.setInfluencingBounds(bounds);
		
		root.addChild(light1);
		root.addChild(light2);
		
		
		
		return root;
	}
	
	@Override
	public void keyPressed(KeyEvent k) {
		double distAtual = 0.0; 
		System.out.println("Pressed: " + k.getKeyChar());
		if(k.getKeyChar()=='a'){
			
			this.objTrans.getTransform(transform);
			this.toMove.setTranslation(new Vector3d(-0.15f,0,0));
			this.transform.mul(this.toMove);
			this.objTrans.setTransform(transform);
			
//			this.transform.setScale(new Vector3d(1,1,1));
//			this.transform.setTranslation(new Vector3d(xLoc-height, height/2, height*(-3)));
//			this.objTrans.setTransform(transform);
		}
		else if(k.getKeyChar()=='d'){
			this.objTrans.getTransform(transform);
			this.toMove.setTranslation(new Vector3d(0.15f,0,0));
			this.transform.mul(this.toMove);
			this.objTrans.setTransform(transform);
//			this.transform.setScale(new Vector3d(1,1,1));
//			this.transform.setTranslation(new Vector3d(xLoc+height, height*2, height/(-3)));
//			this.objTrans.setTransform(transform);
		}
		else if(k.getKeyCode()==KeyEvent.VK_SPACE){
			int t = 1;
			if(this.aimed){
				while(distAtual<maxDistance+altura){
					this.objTrans.getTransform(transform);
					this.toMove.setTranslation(new Vector3d(0f,-0.005*t*t,-(puxada*t)));
					this.transform.mul(this.toMove);
					this.objTrans.setTransform(transform);
					distAtual+=this.step*(0.005*t*t);
					t++;
					try {
						Thread.sleep(sleep);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				aimed=false;
			}
			else{
				this.toMove.setTranslation(posInicial);
				this.objTrans.setTransform(toMove);
				this.aimed=true;
				this.puxada = 0.0f;
				this.altura = 0.0f;
			}
		}
		else if(k.getKeyCode()==KeyEvent.VK_S){
			this.puxada += 0.01f;
			this.objTrans.getTransform(transform);
			this.toMove.setTranslation(new Vector3d(0,0,puxada));
			this.transform.mul(this.toMove);
			this.objTrans.setTransform(transform);
		}
		else if(k.getKeyCode()==KeyEvent.VK_W){
			this.altura+=0.1f;
			this.objTrans.getTransform(transform);
			this.toMove.setTranslation(new Vector3d(0,altura,0));
			this.transform.mul(this.toMove);
			this.objTrans.setTransform(transform);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent k) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == startBtn){
			if(!timer.isRunning())
				timer.start();
			else
				timer.stop();
		}
		else{
			height += .1f * sign;
			
			if(Math.abs(height*2)>=1)
				sign *= -1.0f;
			
			if(height < -.4f){
				transform.setScale(new Vector3d(0.8,1,.5));
			}
			else if(height > .4f){
				transform.setScale(new Vector3d(.8,1,.5));
			}
			else{
				transform.setScale(new Vector3d(1,1,1));
			}
//			transform.setTranslation(new Vector3d(xLoc+height, height/2, height*(-3)));
			this.objTrans.getTransform(transform);
			this.toMove.setTranslation(new Vector3d(0.1,0.1,0.1));
			this.transform.mul(this.toMove);
			this.objTrans.setTransform(transform);
			objTrans.setTransform(transform);
		}
	}
	
	public static void main(String[] args) {
		BouncyBall b = new BouncyBall();
		MainFrame frame = new MainFrame(b, 800,600);
		frame.setTitle("Canvas Demo");
	}
	
	
}
