package ar.com.rp.ui.pantalla;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.ManejoPantalla;
import ar.com.rp.ui.interfaces.DialogoInterface;
import ar.com.rp.ui.interfaces.ManejoPantallaCall;

public abstract class BaseViewDialog extends JDialog implements ManejoPantallaCall{

	/**
	 * 
	 */
	private ManejoPantalla manejoPantalla = new ManejoPantalla();
	private DialogoInterface dialogoInterface;
	
	private static final long serialVersionUID = 1L;


	public BaseViewDialog() throws Exception {
		super();
		this.setFont(Common.getStandarFont());
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	public void setDialogoInterface(DialogoInterface dialogoInterface) {
		this.dialogoInterface = dialogoInterface;
	}

	public void initialize() throws Exception {
		this.setVisible(true);
	}

	public void asignarBotonAccion(AbstractButton boton, String accion) {
		manejoPantalla.asignarBotonAccion(boton, accion);
	}

	
	public void setActionListener(ActionListener actionListener) {
		manejoPantalla.setActionListener(actionListener);
	}

	
	public boolean presionoTecla(KeyEvent ke) {
		if(dialogoInterface != null) {
			return dialogoInterface.presionoTecla(ke);
		} else {
			return false;
		}
	}

}
