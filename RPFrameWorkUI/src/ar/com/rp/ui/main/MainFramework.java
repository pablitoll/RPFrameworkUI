package ar.com.rp.ui.main;

import java.awt.Font;
import java.net.ServerSocket;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import ar.com.rp.ui.common.Common;

public abstract class MainFramework {

	/**
	 * Launch the application.
	 */

	private static ServerSocket s = null;

	private static Splash splash = null;

	public static void inicializarFont() throws Exception{
		System.setProperty("com.sun.xml.bind.v2.runtime.JAXBContextImpl.fastBoot", "true");

		splashMsg("Interface");
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to
			// another look and feel.
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		// Esto es para que el boton por default sea el que esta
		// seleccionado
		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);

		splashMsg("Cargando tipo de letra");

		setUIFont(Common.getStandarFont());

	}
	
	public static void setUIFont(Font font) {
		javax.swing.plaf.FontUIResource f = new javax.swing.plaf.FontUIResource(font);

		UIManager.getLookAndFeelDefaults().put("defaultFont", font);

		UIManager.getLookAndFeelDefaults().put("defaultFont", f);
		UIManager.getLookAndFeelDefaults().put("Button.font", f);
		UIManager.getLookAndFeelDefaults().put("ToggleButton.font", f);
		UIManager.getLookAndFeelDefaults().put("RadioButton.font", f);
		UIManager.getLookAndFeelDefaults().put("CheckBox.font", f);
		UIManager.getLookAndFeelDefaults().put("ColorChooser.font", f);
		UIManager.getLookAndFeelDefaults().put("ComboBox.font", f);
		UIManager.getLookAndFeelDefaults().put("Label.font", f);
		UIManager.getLookAndFeelDefaults().put("List.font", f);
		UIManager.getLookAndFeelDefaults().put("MenuBar.font", f);
		UIManager.getLookAndFeelDefaults().put("MenuItem.font", f);
		UIManager.getLookAndFeelDefaults().put("RadioButtonMenuItem.font", f);
		UIManager.getLookAndFeelDefaults().put("CheckBoxMenuItem.font", f);
		UIManager.getLookAndFeelDefaults().put("Menu.font", f);
		UIManager.getLookAndFeelDefaults().put("PopupMenu.font", f);
		UIManager.getLookAndFeelDefaults().put("OptionPane.font", f);
		UIManager.getLookAndFeelDefaults().put("Panel.font", f);
		UIManager.getLookAndFeelDefaults().put("ProgressBar.font", f);
		UIManager.getLookAndFeelDefaults().put("ScrollPane.font", f);
		UIManager.getLookAndFeelDefaults().put("Viewport.font", f);
		UIManager.getLookAndFeelDefaults().put("TabbedPane.font", f);
		UIManager.getLookAndFeelDefaults().put("Table.font", f);
		UIManager.getLookAndFeelDefaults().put("TableHeader.font", f);
		UIManager.getLookAndFeelDefaults().put("TextField.font", f);
		UIManager.getLookAndFeelDefaults().put("PasswordField.font", f);
		UIManager.getLookAndFeelDefaults().put("TextArea.font", f);
		UIManager.getLookAndFeelDefaults().put("TextPane.font", f);
		UIManager.getLookAndFeelDefaults().put("EditorPane.font", f);
		UIManager.getLookAndFeelDefaults().put("TitledBorder.font", f);
		UIManager.getLookAndFeelDefaults().put("ToolBar.font", f);
		UIManager.getLookAndFeelDefaults().put("ToolTip.font", f);
		UIManager.getLookAndFeelDefaults().put("Tree.font", f);
		UIManager.getLookAndFeelDefaults().put("InternalFrame.titleFont", Common.getStandarFontBold());
	}

	protected static boolean isRunning(int puerto) {
		boolean retorno = true;
		try {
			if (s == null) {
				s = new ServerSocket(puerto);
				retorno = false;
			}
		} catch (Exception e) {
			// port taken, so app is already running
		}
		return retorno;
	}

	protected static void splashMsg(String mensage) {
		if(splash == null) {
			splash = new Splash();
		}
		
		splash.refreshSplash(mensage);
	}
	
	protected static void regenerarSplash() {
		splash = null;
		
	}

}