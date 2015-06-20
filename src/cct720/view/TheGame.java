package cct720.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.j3d.Canvas3D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import cct720.control.BeginGameControl;

public class TheGame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel labelQuantidadeBolas;
	private JLabel labelPontos;
	private JLabel labelVelocidade;
	private JLabel labelGravidade;
	private JButton btReiniciar;
	private JButton btConfigurar;
	private JButton btExit;
	private JPanel extPanel;
	private JPanel infoPanel;
	private JPanel btPanel;
	private JPanel gamePanel;
	
	public TheGame(BeginGameControl game, Canvas3D canvas, final int PWIDTH, final int PHEIGHT){
		super("The Game");
		
		this.infoPanel = new JPanel();
		this.btPanel = new JPanel();
		this.extPanel = new JPanel();
		
		this.btReiniciar = new JButton("Reiniciar");
		this.btReiniciar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				game.btRestart();
			}
		});
		this.btConfigurar = new JButton("Configurar");
		this.btConfigurar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				game.btConfig();
			}
		});
		this.btExit = new JButton("Sair");
		this.btExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				game.btExit();
			}
		});
		
		this.labelPontos = new JLabel("0 Ponto(s)");
		this.labelQuantidadeBolas = new JLabel("0");
		this.labelGravidade = new JLabel("0");
		this.labelVelocidade = new JLabel("0");
		
		this.infoPanel.setLayout(new GridLayout(4,1));
		this.infoPanel.add(labelPontos);
		this.infoPanel.add(labelQuantidadeBolas);
		this.infoPanel.add(labelGravidade);
		this.infoPanel.add(labelVelocidade);
		
		this.btPanel.setLayout(new GridLayout(3,1));
		this.btPanel.add(btConfigurar, BorderLayout.CENTER);
		this.btPanel.add(btReiniciar, BorderLayout.CENTER);
		this.btPanel.add(btExit, BorderLayout.CENTER);
		
		this.extPanel.setLayout(new BorderLayout());
		this.extPanel.add(this.infoPanel, BorderLayout.PAGE_START);
		this.extPanel.add(this.btPanel, BorderLayout.PAGE_END);
		
		this.gamePanel = new JPanel();
		this.gamePanel.setLayout(new BorderLayout());
		this.gamePanel.setOpaque(false);
		this.gamePanel.setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
		this.gamePanel.add("Center", canvas);
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		c.add(gamePanel, BorderLayout.CENTER);
		c.add(extPanel, BorderLayout.LINE_END);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(true); // fixed size display
		setVisible(true);
	}

	public void updateQtdBolas(int qtd){
		this.getLabelQuantidadeBolas().setText(String.format("   Quantidade de bolas: %02d   ", qtd));
	}
	
	public void updatePontos(int qtd){
		this.labelPontos.setText(String.format("%03d Ponto(s)   ", qtd));
	}
	
	public void updateVelocidade(int qtd){
		this.labelVelocidade.setText(String.format("Velocidade: %03d m/s   ", qtd));
	}
	
	public void updateGravidade(int qtd){
		this.labelGravidade.setText(String.format("Gravidade: %03d m/s²   ", qtd));
	}
	
	public JLabel getLabelQuantidadeBolas() {
		return labelQuantidadeBolas;
	}

	public void setLabelQuantidadeBolas(JLabel labelQuantidadeBolasValor) {
		this.labelQuantidadeBolas = labelQuantidadeBolasValor;
	}
	
	
}

