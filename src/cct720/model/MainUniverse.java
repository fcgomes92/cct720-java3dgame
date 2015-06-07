package cct720.model;

import java.awt.GraphicsConfiguration;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class MainUniverse extends SimpleUniverse{
	
	private BranchGroup sceneBG;
	private BoundingSphere bounds; // for environment nodes
	
	private static final Point3d USERPOSN = new Point3d(0, 30, 0);
	private Vector3d posInicialCamera = new Vector3d(0, 25, 25);
	
	private static final int BOUNDSIZE = 1000; // larger than world
	
	public MainUniverse(Canvas3D canvas){
		super(canvas);
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();
		
		createSceneGraph();
		initUserPosition(); // set user's viewpoint
		orbitControls(canvas); // controls for moving the viewpoint

		this.addBranchGraph(sceneBG);
	}
	
	private void createSceneGraph(){
		sceneBG = new BranchGroup();
		bounds = new BoundingSphere(new Point3d(0, 0, 0), BOUNDSIZE);

		lightScene(); // add the lights
		addBackground(); // add the sky
		sceneBG.addChild(new Chao().getBG()); // add the floor
		
		sceneBG.compile(); // fix the scene
	}
	
	private void lightScene(){
		Color3f white = new Color3f(1.0f, 1.0f, 1.0f);

		// Set up the ambient light
		AmbientLight ambientLightNode = new AmbientLight(white);
		ambientLightNode.setInfluencingBounds(bounds);
		sceneBG.addChild(ambientLightNode);

		// Set up the directional lights
		Vector3f light1Direction = new Vector3f(-5.0f, -5.0f, -5.0f);
		// left, down, backwards
		Vector3f light2Direction = new Vector3f(5.0f, -5.0f, 5.0f);
		// right, down, forwards

		DirectionalLight light1 = new DirectionalLight(white, light1Direction);
		light1.setInfluencingBounds(bounds);
		sceneBG.addChild(light1);

		DirectionalLight light2 = new DirectionalLight(white, light2Direction);
		light2.setInfluencingBounds(bounds);
		sceneBG.addChild(light2);
	}

	private void addBackground(){
		Background back = new Background();
		back.setApplicationBounds(bounds);
		back.setColor(0.17f, 0.65f, 0.92f); // sky colour
		sceneBG.addChild(back);
	}

	private void orbitControls(Canvas3D c){
		OrbitBehavior orbit = new OrbitBehavior(c, OrbitBehavior.REVERSE_ALL);
		orbit.setSchedulingBounds(bounds);

		ViewingPlatform vp = this.getViewingPlatform();
		vp.setViewPlatformBehavior(orbit);
	}

	private void initUserPosition(){
		ViewingPlatform vp = this.getViewingPlatform();
		TransformGroup steerTG = vp.getViewPlatformTransform();

		Transform3D t3d = new Transform3D();
		steerTG.getTransform(t3d);

		// args are: viewer posn, where looking, up direction
		t3d.lookAt(USERPOSN, new Point3d(0, 0, 0), this.posInicialCamera);
		t3d.invert();

		steerTG.setTransform(t3d);
	}
}
