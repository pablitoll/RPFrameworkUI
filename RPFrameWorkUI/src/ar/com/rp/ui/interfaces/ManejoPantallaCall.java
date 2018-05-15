package ar.com.rp.ui.interfaces;

import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

public interface ManejoPantallaCall {

	public abstract void asignarBotones();
	
	public void asignarBotonAccion(AbstractButton boton, String accion);
	
	public void setActionListener(ActionListener actionListener);

}
