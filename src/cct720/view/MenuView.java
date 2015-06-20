package cct720.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cct720.control.MenuControl;

public class MenuView {
	private JFrame frame;
	private JPanel panel;
	private JPanel panelButton;
	private JButton btExit;
	private JButton btStart;
	private JButton btHelp;
	private JLabel titulo;
	private MenuControl menuControl;
	
	public MenuView (MenuControl mc){
		this.menuControl = mc;
		this.frame = new JFrame();
		this.panel = new JPanel();
		this.panelButton = new JPanel();
		this.titulo = new JLabel("THE GAME!");
		
		// Inicialização dos botões
		this.btExit = new JButton("Sair");
		this.btStart = new JButton("Começar");
		this.btHelp = new JButton("Ajuda");
		
		// Action listeners dos botoẽs
		this.btExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				menuControl.btExitControl();
			}
		});
		this.btStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				menuControl.btStartControl();
			}
		});
		this.btHelp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				menuControl.btHelpControl();
			}
		});

		GridLayout gLayout = new GridLayout(4,1);
		this.panelButton.setLayout(gLayout);
		
		this.panelButton.add(this.titulo);
		this.panelButton.add(this.btStart);
		this.panelButton.add(this.btHelp);
		this.panelButton.add(this.btExit);
		
		this.panel.add(this.panelButton);
		
		this.frame.add(this.panel);
		this.frame.setTitle("THE GAME!");
		this.frame.setSize(new Dimension(200,150));
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
		this.frame.setVisible(true);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	
}