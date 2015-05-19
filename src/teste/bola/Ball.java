package teste.bola;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Ball {
	
	public DirectionalLight light1;
	
	public Ball(){
		SimpleUniverse universe = new SimpleUniverse();
		BranchGroup group = new BranchGroup();
		
		Sphere sphere = new Sphere(0.5f);
		group.addChild(sphere);
		
		BoundingSphere bounds = new BoundingSphere(new Point3d(0,0,0), 100);
		
		// Definições da luz direcional
		Color3f light1Color = new Color3f(3f,1f,1f);
		Vector3f light1Direction = new Vector3f(10f,10f,-12f);
		light1 = new DirectionalLight(light1Color, light1Direction);
		
		light1.setInfluencingBounds(bounds);
		
		group.addChild(light1);
		
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(group);
	}
	
	public static void main(String[] args) {
		Ball b = new Ball();	
		for (int i = 0; i < 10; i++) {
			b.light1.setDirection(new Vector3f((float)i,0f,-10f));
		}
	}
}
