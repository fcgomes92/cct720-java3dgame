package cct720.control;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.Canvas3D;
import javax.swing.JPanel;

import cct720.model.MainUniverse;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class BeginGame extends JPanel implements KeyListener {
	
	private static final int PWIDTH = 800;
	private static final int PHEIGHT = 600;
	
	public BeginGame(){
		// Gerar Panel
		setLayout(new BorderLayout());
		setOpaque(false);
		setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
		// Gerar Canvas3D
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();
		Canvas3D canvas3D = new Canvas3D(config);
		add("Center", canvas3D);
		canvas3D.addKeyListener(this);
		canvas3D.setFocusable(true); // give focus to the canvas
		canvas3D.requestFocus();
		// Gerar UniversoPrincipal
		MainUniverse su = new MainUniverse(canvas3D);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
