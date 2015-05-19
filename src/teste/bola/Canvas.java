package teste.bola;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Canvas extends Applet{
	public Canvas(){
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		
		Canvas3D canvas = new Canvas3D(config);
		
		add("Center",canvas);
		
		SimpleUniverse universe = new SimpleUniverse(canvas);
		BranchGroup group = new BranchGroup();
		
		group.addChild(new ColorCube(.5));
		
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(group);
	}
	
	public static void main(String[] args) {
		Canvas c = new Canvas(); 
		MainFrame frame = new MainFrame(c, 500,500);
		frame.setTitle("Canvas Demo");
	}
}
