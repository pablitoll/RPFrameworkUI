package ar.com.rp.ui.pantalla;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.KeyEvent;

import ar.com.rp.ui.common.FrameworkCommon;
import ar.com.rp.ui.interfaces.DialogoInterface;
import ar.com.rp.ui.interfaces.PermisosInterface;

public abstract class BaseControllerDialog<TipoPatPrincipal extends BasePantallaPrincipal<?,?>, TipoVista extends BaseViewDialog, TipoModelo extends BaseModel> extends BaseController
		implements DialogoInterface {

	private TipoVista view;
	private TipoModelo model;
	private TipoPatPrincipal basePantallaPrincipal = null;

	protected abstract void cargaPantalla() throws Exception;
	protected abstract String getResultado() throws Exception;

	public BaseControllerDialog(TipoPatPrincipal pantPrincipal, TipoVista view, TipoModelo model, PermisosInterface permisos) throws Exception {
		super(permisos);
		this.view = view;
		this.model = model;
		this.basePantallaPrincipal = pantPrincipal;
		this.view.setDialogoInterface(this);

		this.view.setActionListener(this);
		this.view.asignarBotones();
	}

	public String iniciar() throws Exception {
		cargarPermisos();
		
		cargaPantalla();
		
		if(basePantallaPrincipal != null) {
			view.setLocationRelativeTo(basePantallaPrincipal.getView().desktopPane);
		}
		
		// Busca el primer componente que puede ser focusable y hace foco
		requestFocusFirstComponent();		
		
		view.initialize();
		
		return getResultado();
	}

	protected TipoModelo getModel() {
		return model;
	}

	public TipoVista getView() {
		return view;
	}

	protected void callPantPrincipal(String accion) {
		if (basePantallaPrincipal != null) {
			basePantallaPrincipal.ejecuarAccion(accion);
		}
	}

	@Override
	protected Container getContenedor() {
		return getView().getContentPane();
	}

	public TipoPatPrincipal getPantallaPrincipal() {
		return basePantallaPrincipal;
	}

	public void setPantallaPrincipal(TipoPatPrincipal basePantallaPrincipal) {
		this.basePantallaPrincipal = basePantallaPrincipal;
	}

	public void restoreCursor() {
		basePantallaPrincipal.restoreCursor();
	}

	public Cursor setCursorOcupado() {
		return basePantallaPrincipal.cursorOcupado();
	}

	public void salir() {
		getView().setVisible(false);
	}

	@Override
	public boolean presionoTecla(KeyEvent ke) {
		return FrameworkCommon.buscarKey(getView().getContentPane(), ke);
	}

	@Override
	protected void cerrarVentana() {
		salir();
	}
}
