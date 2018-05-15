package ar.com.rp.ui.pantalla;

import java.awt.Container;
import java.awt.Cursor;

import javax.swing.JDesktopPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import ar.com.rp.ui.error.ErrorManager;
import ar.com.rp.ui.interfaces.PermisosInterface;

public abstract class BaseControllerMVC<TipoPatPrincipal extends BasePantallaPrincipal<?,?>, TipoVista extends BaseViewMVC, TipoModelo extends BaseModel> extends BaseController {

	private TipoVista view;
	private TipoModelo model;
	private JDesktopPane panel;
	private boolean iniciado = false;
	private TipoPatPrincipal basePantallaPrincipal = null;

	protected abstract void resetearPantalla() throws Exception;

	public BaseControllerMVC(JDesktopPane desktop, TipoVista view, TipoModelo model, PermisosInterface permisos) throws Exception {
		super(permisos);
		crearBaseControllerMVC(null, desktop, view, model);		
	}

	public BaseControllerMVC(TipoPatPrincipal pantPrincipal, TipoVista view, TipoModelo model, PermisosInterface permisos) throws Exception {
		super(permisos);
		crearBaseControllerMVC(pantPrincipal, pantPrincipal.getView().desktopPane, view, model);
	}

	private void crearBaseControllerMVC(TipoPatPrincipal pantPrincipal, JDesktopPane desktop, TipoVista view, TipoModelo model) throws Exception {
		this.view = view;
		this.model = model;
		this.panel = desktop;
		this.basePantallaPrincipal = pantPrincipal;

		view.setActionListener(this);
		view.asignarBotones();

		view.eventoInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent arg0) {
				cerrarPantalla();
			}
		});
		
	}

	public void findOrCreated() throws Exception {

		if (iniciado) {
			GUIUtils.moveToFront(this.view);
		} else {
			iniciado = true;
			iniciar();
		}

		resetearPantalla();

		// Busca el primer componente que puede ser focusable y hace foco
		requestFocusFirstComponent();

		onFocus();
	}

	protected void iniciar() throws Exception {
		panel.add(view);
		view.initialize();
		cargarPermisos();
	}

	public boolean isActivo() {
		return view.isVisible();
	}
	
	@Override
	protected void cerrarVentana() {
		cerrarPantalla();
	}

	protected void cerrarPantalla() {
		view.setVisible(false);
		try {
			foucusPrincipal();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				ErrorManager.showError(getPantallaPrincipal(), "Error de Framework", "Error al cerrar pantalla", e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	protected void foucusPrincipal() throws Exception {
		if (basePantallaPrincipal != null) {
			basePantallaPrincipal.onFocus();
		}
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

	protected void onFocus() throws Exception {
		// Para implementar el que lo quieras
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
		if (basePantallaPrincipal != null) {
			basePantallaPrincipal.restoreCursor();
		}
	}

	public Cursor setCursorOcupado() {
		if (basePantallaPrincipal != null) {
			return basePantallaPrincipal.cursorOcupado();
		} else {
			return null;
		}
	}

}
