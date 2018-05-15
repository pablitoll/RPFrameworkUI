package ar.com.rp.ui.common;

import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.text.JTextComponent;

import ar.com.rp.ui.componentes.JButtonRP;
import ar.com.rp.ui.pantalla.BasePantallaPrincipalView;

public class FrameworkCommon {

	public static boolean buscarKey(Container contenedor, KeyEvent ke) {
		boolean retorno = false;

		Component[] component = contenedor.getComponents();

		for (int i = 0; (i < component.length) && !retorno; i++) {

			if (component[i] instanceof JButtonRP) {
				JButtonRP button = (JButtonRP) component[i];
				if ((button.getMnemonic() == ke.getKeyCode()) && (ke.isControlDown() == button.isMnemonicControl()) && (ke.isAltDown() == button.isMnemonicAlt())
						&& (ke.isShiftDown() == button.isMnemonicShift()) && button.isVisible() && button.isEnabled()) {
					retorno = true;
					button.doClick();

				}
			}
			if (!retorno && (component[i] instanceof Container) && !(component[i] instanceof JDesktopPane)) {
				if (((Container) component[i]).isVisible()) {
					retorno = buscarKey((Container) component[i], ke);
				}
			}

		}

		return retorno;
	}

	public static void requestFocusFirstComponent(Container contenedor) {
		List<Component> lista = getAllComponents(contenedor);
		Component candidato = null;
		for (Component comp : lista) {
			if (comp.isFocusable() && comp.isEnabled() && comp.isShowing()) {
				if ((candidato == null) || (comp.getLocationOnScreen().getY() < candidato.getLocationOnScreen().getY())
						|| ((comp.getLocationOnScreen().getY() == candidato.getLocationOnScreen().getY())
								&& (comp.getLocationOnScreen().getX() < candidato.getLocationOnScreen().getX()))) {
					candidato = comp;
				}
			}
		}
		if (candidato != null) {
			candidato.requestFocusInWindow();
			if (candidato instanceof JTable) {
				if (((JTable) candidato).getModel().getRowCount() > 0) {
					((JTable) candidato).changeSelection(0, 0, false, false);
				}
			}
		}
	}

	private static List<Component> getAllComponents(final Container c) {
		if (c instanceof JTabbedPane) {
			JTabbedPane pane = (JTabbedPane) c;
			return getAllComponents((Container) pane.getSelectedComponent());
		} else {

			Component[] comps = c.getComponents();
			List<Component> compList = new ArrayList<Component>();
			for (Component comp : comps) {
				if (comp.isVisible()) {
					if ((comp instanceof JTextComponent) || (comp instanceof AbstractButton) || (comp instanceof JTable) || (comp instanceof JTree) || (comp instanceof JList)
							|| (comp instanceof JComboBox)) {
						compList.add(comp);
					} else {
						compList.addAll(getAllComponents((Container) comp));
					}
				}
			}
			return compList;
		}
	}
	
	public static void ponerADerecha(JFrame pantalla, int anchoMaximo, int altoMaximo, BasePantallaPrincipalView pantallaPrincipal) {
		Rectangle screen = pantallaPrincipal.getGraphicsConfiguration().getBounds();

		int alto = altoMaximo;

		if ((screen.getHeight() - 40) < altoMaximo) {
			alto = screen.height - 40;
		}

		int x = (screen.width - anchoMaximo);
		int y = (screen.height - 40 - alto) / 2;

		pantalla.setBounds(x, y, anchoMaximo, alto);
		pantalla.setLocation(pantallaPrincipal.getGraphicsConfiguration().getBounds().x + x, y);

	}

}
