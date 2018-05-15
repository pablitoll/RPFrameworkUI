package ar.com.rp.ui.pantalla;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.JButtonRP;

public abstract class BaseViewMVCExtendida extends BaseViewMVC {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JButtonRP btnCerrar;
	protected JPanel pnlInferiorBotones;

	public abstract void asignarBotonesPantExtendida();

	public BaseViewMVCExtendida() throws Exception {
		super();
		crearBotonesInterno();
		crearBotonesPantalla(pnlInferiorBotones);
	}

	public BaseViewMVCExtendida(Boolean crearBotones) throws Exception {
		super();

		if (crearBotones) {
			crearBotonesInterno();
			crearBotonesPantalla(pnlInferiorBotones);
		}
	}

	private void crearBotonesInterno() {
		pnlInferiorBotones = new JPanel();
		pnlInferiorBotones.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(pnlInferiorBotones, BorderLayout.SOUTH);
		pnlInferiorBotones.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
	}

	protected void crearBotonesPantalla(JPanel panel) {
		btnCerrar = new JButtonRP("Cerrar");
		btnCerrar.setMnemonic(KeyEvent.VK_ESCAPE);
		btnCerrar.setFont(Common.getStandarFont());
		panel.add(btnCerrar);

	}

	@Override
	public void asignarBotones() {
		asignarBotonAccion(btnCerrar, "CERRAR_VENTANA_FRAMEWORK");
		asignarBotonesPantExtendida();
	}

}
