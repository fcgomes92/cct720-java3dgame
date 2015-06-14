package cct720.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.media.j3d.Canvas3D;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cct720.control.BeginGameControl;

public class TheGame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel labelQuantidadeBolas;
	private JPanel gamePanel;
	
	public TheGame(BeginGameControl game, Canvas3D canvas, final int PWIDTH, final int PHEIGHT){
		super("The Game");
		
		this.labelQuantidadeBolas = new JLabel("0");
		
		this.gamePanel = new JPanel();
		this.gamePanel.setLayout(new BorderLayout());
		this.gamePanel.setOpaque(false);
		this.gamePanel.setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
		this.gamePanel.add("Center", canvas);
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		c.add(gamePanel, BorderLayout.CENTER);
		c.add(labelQuantidadeBolas, BorderLayout.PAGE_END);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(false); // fixed size display
		setVisible(true);
	}

	public void updateQtdBolas(int qtd){
		this.getLabelQuantidadeBolas().setText("Quantidade de bolas: " + qtd);
	}
	
	public JLabel getLabelQuantidadeBolas() {
		return labelQuantidadeBolas;
	}

	public void setLabelQuantidadeBolas(JLabel labelQuantidadeBolasValor) {
		this.labelQuantidadeBolas = labelQuantidadeBolasValor;
	}
}

