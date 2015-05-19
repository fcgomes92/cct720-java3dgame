package teste.bola;

import javax.media.j3d.BranchGroup;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class World {
	public World(){
		SimpleUniverse universe = new SimpleUniverse();
		BranchGroup group = new BranchGroup();
		
		group.addChild(new ColorCube(.5));
		
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(group);
	}
	
	public static void main(String[] args) {
		new Ball();	
	}
}
