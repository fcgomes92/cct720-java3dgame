package cct720.control;

import javax.swing.JOptionPane;

import cct720.view.ConfigView;

public class ConfigControl {
	private float gravidade;
	private ConfigView configDialog = new ConfigView();
	
	public void showConfigPanel(BeginGameControl bgc){
		int result = JOptionPane.showConfirmDialog(null,
				this.configDialog.getPanelMain(), "Configuração",
				JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION && this.configDialog.getGravidade() > 0.0f ){
			bgc.alterarGravidade(this.configDialog.getGravidade());
		}
	}
}
