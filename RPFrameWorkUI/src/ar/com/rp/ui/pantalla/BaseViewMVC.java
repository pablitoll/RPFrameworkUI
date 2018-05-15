package ar.com.rp.ui.pantalla;

import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;

import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.ManejoPantalla;
import ar.com.rp.ui.interfaces.ManejoPantallaCall;

public abstract class BaseViewMVC extends JInternalFrame implements ManejoPantallaCall{

	/**
	 * 
	 */
	private ManejoPantalla manejoPantalla = new ManejoPantalla();
	
	private static final long serialVersionUID = 1L;


	public BaseViewMVC() throws Exception {
		super();
		this.setFont(Common.getStandarFont());
		this.setClosable(true);
		this.setMaximizable(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSelected(true);
		
	}

	public void initialize() throws Exception {
		this.setVisible(true);
		this.setMaximum(true);
	}

	public void eventoInternalFrameListener(InternalFrameAdapter ifa) {
		this.addInternalFrameListener(ifa);
	}

	public void asignarBotonAccion(AbstractButton boton, String accion) {
		manejoPantalla.asignarBotonAccion(boton, accion);
	}

	
	public void setActionListener(ActionListener actionListener) {
		manejoPantalla.setActionListener(actionListener);
	}
}
