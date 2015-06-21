package cct720.view;

import java.awt.GridLayout;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConfigView {
	private JPanel panelMain;
	private JPanel panelCampos;

	private GridLayout layoutGeral;
	private GridLayout layoutCampos;

	private JLabel lblGravidade;
	private JFormattedTextField txtGravidade;
	
	public ConfigView(){
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMaximumIntegerDigits(5);
		
		this.lblGravidade = new JLabel("Gravidade (m/s²)");
		this.txtGravidade = new JFormattedTextField(format);
		
		this.layoutGeral = new GridLayout(1,1,5,5);
		this.layoutCampos = new GridLayout(2,2);
		
		this.panelMain = new JPanel();
		this.panelMain.setLayout(layoutGeral);
		this.panelCampos = new JPanel();
		this.panelCampos.setLayout(layoutCampos);
		
		this.panelCampos.add(this.lblGravidade,0,0);
		this.panelCampos.add(this.txtGravidade,0,1);
		
		this.panelMain.add(this.panelCampos,0);
	}

	public JPanel getPanelMain() {
		return panelMain;
	}

	public void setPanelMain(JPanel panelMain) {
		this.panelMain = panelMain;
	}
	
	public float getGravidade(){
		return Float.parseFloat(this.txtGravidade.getText().replace(",", "."));
	}
}
