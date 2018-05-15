package ar.com.rp.ui.componentes;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;

import ar.com.rp.ui.interfaces.LoguerInterface;

public class JButtonRP extends JButton {

	private static final long serialVersionUID = 1L;
	private boolean tienePermisos = true;
	private boolean visible = true;
	private boolean mnemonicControl = false;
	private boolean mnemonicShift = false;
	private boolean mnemonicAlt = false;
	private String toolTipTextOrg = "";
	private String toolTipTextTecla = "";
	private String nombreVentana = null;
	protected LoguerInterface loguerInterface;
	// Esto es para los botones que pueden cambiar su nombre, asi siempre cargo el mismo nombre en la pantalla de permisos
	private String nombreBoton = "";

	
	public LoguerInterface getLoguerInterface() {
		return loguerInterface;
	}

	public void setLoguerInterface(LoguerInterface loguerInterface) {
		this.loguerInterface = loguerInterface;
	}

	public String getNombreBoton() {
		return nombreBoton;
	}

	public void setNombreBoton(String nombreBoton) {
		this.nombreBoton = nombreBoton;
	}

	@Override
	protected void fireActionPerformed(ActionEvent e) {
		keyLoger();
		super.fireActionPerformed(e);
	}
	
	public JButtonRP(String caption) {
		super(caption);
	}

	public JButtonRP(String caption, String nombreBoton) {
		super(caption);
		this.nombreBoton = nombreBoton;
	}

	@Override
	public void setToolTipText(String text) {
		toolTipTextOrg = text;
		setHint();
	}

	@Override
	public void setMnemonic(int mnemonic) {
		super.setMnemonic(mnemonic);
		String teclaFuncion = getTeclaFuncion(mnemonic);
		String teclaControl = getTeclaControl(mnemonic);
		String teclaShift = getTeclaShift(mnemonic);
		String teclaAlt = getTeclaAlt(mnemonic);
		toolTipTextTecla = teclaShift + teclaControl + teclaAlt + teclaFuncion;
		setHint();
	}

	private void setHint() {
		String hint = "";
		if (this.isEnabled() && !toolTipTextTecla.equals("")) {
			hint = toolTipTextTecla;
		}

		if (!toolTipTextOrg.equals("") && !hint.equals("")) {
			hint = hint + " - " + toolTipTextOrg;
		}
		super.setToolTipText(hint.equals("") ? null : hint);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		setHint();
	}

	private String getTeclaControl(int mnemonic) {
		if (isMnemonicControl()) {
			return "Control + ";
		} else {
			return "";
		}
	}

	private String getTeclaShift(int mnemonic) {
		if (isMnemonicShift()) {
			return "Shift + ";
		} else {
			return "";
		}
	}

	private String getTeclaAlt(int mnemonic) {
		if (isMnemonicAlt()) {
			return "Alt + ";
		} else {
			return "";
		}
	}

	private String getTeclaFuncion(int mnemonic) {
		switch (mnemonic) {
			case KeyEvent.VK_F1:
				return "F1";
			case KeyEvent.VK_F2:
				return "F2";
			case KeyEvent.VK_F3:
				return "F3";
			case KeyEvent.VK_F4:
				return "F4";
			case KeyEvent.VK_F5:
				return "F5";
			case KeyEvent.VK_F6:
				return "F6";
			case KeyEvent.VK_F7:
				return "F7";
			case KeyEvent.VK_F8:
				return "F8";
			case KeyEvent.VK_F9:
				return "F9";
			case KeyEvent.VK_F10:
				return "F10";
			case KeyEvent.VK_F11:
				return "F11";
			case KeyEvent.VK_F12:
				return "F12";
			case KeyEvent.VK_ESCAPE:
				return "ESC";
			default:
				if ((mnemonic >= KeyEvent.VK_A) && (mnemonic <= KeyEvent.VK_Z)) {
					return Character.toString((char) mnemonic);
				} else {
					return "";
				}
		}
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
		boolean setear = tienePermisos && visible;
		super.setVisible(setear);
	}

	public void tienePermiso(boolean tienePermisos) {
		this.tienePermisos = tienePermisos;
		// this.setVisible(this.isVisible()); // le vuelvo a pasar su estado para que refresque
		// Si lo programe para que sea visible, le actualizo el permisos
		// caso contrario no lo actualizo aunque tenga permisos para hacerlo

		if (visible) {
			super.setVisible(tienePermisos);
		}
	}

	public String getNombreComponente() {
		if (nombreBoton.equals("")) {
			return super.getText();
		} else {
			return nombreBoton;
		}
	}

	@Override
	public void doClick() {
		if (tienePermisos && isVisible() && isEnabled()) {
			super.doClick();
		}
	}

	public boolean isMnemonicControl() {
		return mnemonicControl;
	}

	public void setMnemonicControl(boolean mnemonicControl) {
		this.mnemonicControl = mnemonicControl;
		setMnemonic(getMnemonic());
	}

	public boolean isMnemonicShift() {
		return mnemonicShift;
	}

	public void setMnemonicShift(boolean mnemonicShift) {
		this.mnemonicShift = mnemonicShift;
		setMnemonic(getMnemonic());
	}

	public boolean isMnemonicAlt() {
		return mnemonicAlt;
	}

	public void setMnemonicAlt(boolean mnemonicAlt) {
		this.mnemonicAlt = mnemonicAlt;
		setMnemonic(getMnemonic());
	}

	private void keyLoger() {
		try {
			if (loguerInterface != null) {

				if (nombreVentana == null) {
					nombreVentana = getPadre(getParent());
				}

				loguerInterface.logKeyLoger("Click boton *" + getNombreComponente() + "* <-> " + nombreVentana);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getPadre(Container componente) {
		if (componente == null) {
			return "S/D";
		}

		if (componente.getClass().getName().contains("ar.com.rp")) {
			String[] nombre = componente.getClass().getName().split("[.]");
			return nombre[nombre.length - 1];
		} else {

			if (componente instanceof JDesktopPane) {
				return "Desktop";
			} else {
				if (componente instanceof JMenu) {
					return "Menu";
				} else {
					if (componente instanceof JFrame) {
						return "Frame";
					} else {
						return getPadre(componente.getParent());
					}
				}
			}
		}
	}
}
