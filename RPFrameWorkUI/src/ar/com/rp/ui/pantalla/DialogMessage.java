package ar.com.rp.ui.pantalla;

import java.awt.AWTKeyStroke;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import ar.com.rp.ui.interfaces.LoguerInterface;

public class DialogMessage {

	public static void showMessageDialog(String message, Image iconoAplicacion, LoguerInterface logKey, BasePantallaPrincipal<?,?> pantPrincipal) throws Exception {
		showMessageDialog(message, "", JOptionPane.INFORMATION_MESSAGE, iconoAplicacion, logKey, pantPrincipal);
	}

	public static void showMessageDialog(Object message, String title, int messageType, Image iconoAplicacion, LoguerInterface logKey, BasePantallaPrincipal<?,?> pantPrincipal)
			throws Exception {
		showConfirmDialog(message, title, JOptionPane.PLAIN_MESSAGE, messageType, null, null, null, iconoAplicacion, logKey, pantPrincipal);
	}

	public static void showMessageDialog(Object dummy, Object message, String title, int messageType, Image iconoAplicacion, LoguerInterface logKey,
			BasePantallaPrincipal<?,?> pantPrincipal) throws Exception {
		showMessageDialog(message, title, messageType, iconoAplicacion, logKey, pantPrincipal);
	}

	public static Integer showConfirmDialog(Object message, String title, int optionType, int messageType, Icon icon, Object[] options, Object initialValue, Image iconoAplicacion,
			LoguerInterface logKey, BasePantallaPrincipal<?,?> pantPrincipal) throws Exception {
		return (Integer) showConfirmDialogObject(message, title, optionType, messageType, icon, options, initialValue, iconoAplicacion, logKey, pantPrincipal);
	}

	public static Object showConfirmDialogObject(Object message, String title, int optionType, int messageType, Icon icon, Object[] options, Object initialValue,
			Image iconoAplicacion, LoguerInterface logKey, BasePantallaPrincipal<?,?> pantPrincipal) throws Exception {
		if (SwingUtilities.isEventDispatchThread()) {

			JOptionPane optionPane = new JOptionPane(message, messageType, optionType, icon, options, initialValue);
			JDialog dialog = optionPane.createDialog(title);

			if (iconoAplicacion != null) {
				dialog.setIconImage(iconoAplicacion);
			}

			if (message.getClass() != Object[].class) {
				Set<AWTKeyStroke> focusTraversalKeysFORWARD = new HashSet<AWTKeyStroke>(dialog.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
				focusTraversalKeysFORWARD.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.VK_UNDEFINED));
				dialog.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, focusTraversalKeysFORWARD);

				Set<AWTKeyStroke> focusTraversalKeysBACKWARD = new HashSet<AWTKeyStroke>(dialog.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
				focusTraversalKeysBACKWARD.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_LEFT, KeyEvent.VK_UNDEFINED));
				dialog.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, focusTraversalKeysBACKWARD);
			}

			if (pantPrincipal != null) {
				dialog.setLocationRelativeTo(pantPrincipal.getView().desktopPane);
			}

			dialog.setVisible(true);
			dialog.dispose();

			Object retorno = JOptionPane.CLOSED_OPTION;
			if (optionPane.getValue() != null) {
				retorno = optionPane.getValue();
			}

			if (logKey != null) {
				logKey.logKeyLoger("Dialog -> " + title + " - " + message + " Boton Selccionado: " + retorno);
			}

			return retorno;
		} else {
			System.out.println("NO MUESTRO ERROR: " + message);
			return 0;
		}

	}

}
