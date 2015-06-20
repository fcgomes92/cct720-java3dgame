package cct720.control;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import cct720.view.MenuView;

public class MenuControl {
	
	private MenuView menuView;
	
	public MenuControl(){
		this.menuView = new MenuView(this);
	}

	public void btExitControl(){
		System.exit(0);
	}
	
	public void btHelpControl(){
		try {
			URI u = new URI("http://fcgomes92.wix.com/teste1");
			Desktop.getDesktop().browse(u);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public void btStartControl(){
		new BeginGameControl();
		this.menuView.getFrame().setVisible(false);
	}
}
