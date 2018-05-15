package ar.com.rp.ui.componentes;

import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

public class ManejoPantalla {
	private ActionListener actionListener;
	
	public ActionListener getActionListener() {
		return actionListener;
	}

	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}

	public void asignarBotonAccion(AbstractButton boton, String accion) {
		boton.setActionCommand(accion);
		boton.addActionListener(actionListener);
	}

	
}
