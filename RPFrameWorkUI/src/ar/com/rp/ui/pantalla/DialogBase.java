package ar.com.rp.ui.pantalla;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.common.FrameworkCommon;

public abstract class DialogBase extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BasePantallaPrincipal<?,?> pantPrincipal;

	protected abstract void cargaPantalla() throws Exception;

	public DialogBase(BasePantallaPrincipal<?,?> pantPrincipal) {
		this.pantPrincipal = pantPrincipal;
		setFont(Common.getStandarFont());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				cerrar();
			}
		});

		addWindowFocusListener(new WindowAdapter() {
			public void windowGainedFocus(WindowEvent e) {
				// Busca el primer componente que puede ser focusable y hace foco
				FrameworkCommon.requestFocusFirstComponent(getContentPane());
			}
		});

	}

	public boolean presionoTecla(KeyEvent ke) {
		return FrameworkCommon.buscarKey(this.getContentPane(), ke);
	}

	public void iniciar() throws Exception {
		cargaPantalla();

		if (pantPrincipal != null) {
			this.setLocationRelativeTo(pantPrincipal.getView().desktopPane);
		}

		setVisible(true);
	}

	protected void cerrar() {
		setVisible(false);
	}
}
