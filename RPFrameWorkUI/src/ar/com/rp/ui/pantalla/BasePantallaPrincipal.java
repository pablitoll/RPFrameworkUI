package ar.com.rp.ui.pantalla;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;

import ar.com.rp.ui.interfaces.LoguerInterface;
import ar.com.rp.ui.interfaces.PermisosInterface;

public abstract class BasePantallaPrincipal<TipoVista extends BasePantallaPrincipalView, TipoModelo extends BaseModel> extends BaseController implements WindowListener {
	// Manejo del cursor
	private int cantSent = 0;
	private Cursor cursorVentanaPrincipal = null;

	protected TipoVista view;

	protected TipoModelo model;

	protected abstract void salir();

	protected ControllerManager cmGestordeVentanas;
	protected LoguerInterface loguerInterface;
	private static Robot robot = null;
	
	public BasePantallaPrincipal(TipoVista view, TipoModelo model, PermisosInterface permisos) throws Exception {
		super(permisos);
		this.model = model;
		this.view = view;

		view.setActionListener(this);
		view.asignarBotones();
		view.eventoWindowListener(this);

		cmGestordeVentanas = new ControllerManager();

		// Para el manejo de las teclas
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				boolean llamarAPrincipal = true;
				if (e.getID() == KeyEvent.KEY_PRESSED) {
					// System.out.println("Got key event!" +" *** " +e.getExtendedKeyCode()+" *** "+ e.getKeyText(e.getKeyCode()));
					try {
						if (loguerInterface != null) {
							loguerInterface.logKeyLoger(KeyEvent.getKeyText(e.getKeyCode()));
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					if (!e.isConsumed()) {
						if ((e.getKeyCode() == KeyEvent.VK_ALT) || isMenuSelected()) {
							presionoTecla(e); // Para acceder al menu
						} else {
							// Me fijo si la pantalla activa es un BaseControllerMVC
							if (KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow() instanceof JFrame) {// Pant Principal
								BaseControllerMVC<?, ?, ?> controller = cmGestordeVentanas.getLastCall();
								if (controller != null) {
									if (controller.presionoTecla(e)) {
										e.consume();
									}
								}
							} else {
								// Me fijo si la pantalla activa es un BaseViewDialog
								if (KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow() instanceof BaseViewDialog) {
									if (((BaseViewDialog) KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow()).presionoTecla(e)) {
										e.consume();
									}
									llamarAPrincipal = false;
								} else {
									// Me fijo si la pantalla activa es un DialogBase
									if (KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow() instanceof DialogBase) {
										if (((DialogBase) KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow()).presionoTecla(e)) {
											e.consume();
										}
									} else {
										// Por ultimo me fijo si la pantalla activa es un showmessaje
										if (KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow() instanceof JDialog) {
											llamarAPrincipal = false;
										}
									}
								}
							}

							if (!e.isConsumed() && llamarAPrincipal) {
								presionoTecla(e);
							}
						}
					}
				}

				return e.isConsumed();
			}
		});

		// Busca el primer componente que puede ser focusable y hace foco
		requestFocusFirstComponent();

	}
	
	@Override
	protected void cerrarVentana() {
		salir();
	}

	// Me fijo si el menu tiene algun submenu seleccionado
	private boolean isMenuSelected() {
		for (int i = 0; i < getView().menuBar.getMenuCount(); i++) {
			if (getView().menuBar.getMenu(i).isSelected()) {
				return true;
			}
		}
		return false;
	}

	protected TipoVista getView() {
		return view;
	}

	protected TipoModelo getModel() {
		return model;
	}

	@Override
	protected Container getContenedor() {
		return view;
	}

	@Override
	public boolean presionoTecla(KeyEvent ke) {
		boolean retorno = super.presionoTecla(ke);

		if (!retorno) {
			if (ke.getKeyCode() == KeyEvent.VK_ALT) {
				JMenu menu = getView().menuBar.getMenu(0);
				if (menu != null) {
					retorno = true;
					ke.consume();
					menu.doClick();
				}
			}
		}
		return retorno;
	}

	public void onFocus() throws Exception {
		cmGestordeVentanas.focusLastCall();
	}

	public void setLoguerInterface(LoguerInterface loguerInterface) {
		this.loguerInterface = loguerInterface;
	}

	public LoguerInterface getLoguerInterface() {
		return loguerInterface;
	}

	public void restoreCursor() {
		cantSent--;
		if (cantSent <= 0) {
			cantSent = 0;
			BaseControllerMVC<?, ?, ?> ultPantalla = cmGestordeVentanas.getLastCall();
			if (ultPantalla != null) {
				ultPantalla.getView().setCursor(cursorVentanaPrincipal);
			}
			getView().setCursor(cursorVentanaPrincipal);
		}
	}

	public Cursor cursorOcupado() {
		Cursor auxCursor;
		cantSent++;
		BaseControllerMVC<?, ?, ?> ultPantalla = cmGestordeVentanas.getLastCall();
		if (ultPantalla != null) {
			auxCursor = ultPantalla.getView().getCursor();
			ultPantalla.getView().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		} else {
			auxCursor = getView().getCursor();
		}
		if (auxCursor != Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)) { // guardo el cursor siempre que ya no este en el cursor ocupado
			cursorVentanaPrincipal = auxCursor;
		}
		getView().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		return cursorVentanaPrincipal;
	}

	public void refrescarPermisosVentanas() throws Exception {
		cmGestordeVentanas.refreshPermisos();
	}

	public BaseControllerMVC<?, ?, ?> getUltimaPantallaCargada() {
		return cmGestordeVentanas.getLastCall();
	}

	public static Robot getRobot() throws Exception {
		if (robot == null) {
			robot = new Robot();
			robot.setAutoDelay(100);
		}

		return robot;
	}

	public void iniciar() throws Exception {
		// Seguridad
		cargarPermisos();

		// muestro la pantalla despues de todo
		getView().initialize();
	}
	
	@Override
	public void windowClosing(WindowEvent arg0) {
		salir();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}

	public JDesktopPane getDesktopPane() {
		return getView().desktopPane;
	}
}
