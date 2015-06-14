package cct720.model;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.Viewer;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class MainUniverse{
	
	public SimpleUniverse su;
	public BranchGroup sceneBG;
	private BoundingBox bounds; // for environment nodes
	
	private static final Point3d USERPOSN = new Point3d(10, 10, 0);
	private Vector3d posInicialCamera = new Vector3d(0, 1, 0);
	
	private static final int BOUNDSIZE = 500; // larger than world
	
	public MainUniverse(Canvas3D canvas){
		// Manually create the viewing platform so that we can customize it
	    ViewingPlatform viewingPlatform = new ViewingPlatform();
	    viewingPlatform.getViewPlatform().setActivationRadius(300f);

	    // Set the view position back far enough so that we can see things
	    TransformGroup viewTransform = viewingPlatform.getViewPlatformTransform();
	    Transform3D t3d = new Transform3D();
	    
	    // Note: Now the large value works
	    t3d.lookAt(new Point3d(0,0,150), new Point3d(0,0,0), new Vector3d(0,1,0));
	    t3d.invert();
	    viewTransform.setTransform(t3d);

	    // Set back clip distance so things don't disappear 
	    Viewer viewer = new Viewer(canvas);
	    View view = viewer.getView();
	    view.setBackClipDistance(300);
		
		su = new SimpleUniverse(viewingPlatform, viewer);
		
		createSceneGraph();
		initUserPosition(); // set user's viewpoint
		orbitControls(canvas); // controls for moving the viewpoint
		
		su.addBranchGraph(sceneBG);
	}
	
	private void createSceneGraph(){
		sceneBG = new BranchGroup();
		sceneBG.setCapability(BranchGroup.ALLOW_DETACH);
		sceneBG.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		sceneBG.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		sceneBG.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		sceneBG.setCapability(BranchGroup.ALLOW_BOUNDS_WRITE);
		sceneBG.setCapability(BranchGroup.ALLOW_BOUNDS_READ);
		bounds = new BoundingBox(new Point3d(-BOUNDSIZE, 0, -BOUNDSIZE), new Point3d(BOUNDSIZE, BOUNDSIZE, BOUNDSIZE));

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
		orbit.setRotationCenter(new Point3d(0, 0, 0));
		orbit.setSchedulingBounds(bounds);

		ViewingPlatform vp = su.getViewingPlatform();
		vp.setViewPlatformBehavior(orbit);
	}

	private void initUserPosition(){
		ViewingPlatform vp = su.getViewingPlatform();	
		TransformGroup steerTG = vp.getViewPlatformTransform();

		Transform3D t3d = new Transform3D();
		steerTG.getTransform(t3d);

		// args are: viewer posn, where looking, up direction
		t3d.lookAt(USERPOSN, new Point3d(0, 0, 0), this.posInicialCamera);
		t3d.invert();

		steerTG.setTransform(t3d);
	}

	public BranchGroup getSceneBG() {
		return sceneBG;
	}

	public void setSceneBG(BranchGroup sceneBG) {
		this.sceneBG = sceneBG;
	}

	public Vector3d getPosInicialCamera() {
		return posInicialCamera;
	}

	public void setPosInicialCamera(Vector3d posInicialCamera) {
		this.posInicialCamera = posInicialCamera;
	}

	public static Point3d getUserposn() {
		return USERPOSN;
	}
}