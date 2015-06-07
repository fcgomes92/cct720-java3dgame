package cct720.view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

import cct720.control.BeginGame;

public class TheGame extends JFrame{
	public TheGame(BeginGame game){
		super("The Game");
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		c.add(game, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(false); // fixed size display
		setVisible(true);
	}
}

