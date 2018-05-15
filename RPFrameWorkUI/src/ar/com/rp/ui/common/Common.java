package ar.com.rp.ui.common;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import ar.com.rp.rpcutils.CommonUtils;

public class Common {

	private static GeneralSettings generalSettings = null;

	public static void setGeneralSettings(GeneralSettings generalSettings) {
		Common.generalSettings = generalSettings;
	}

	public static GeneralSettings getGeneralSettings() {
		if (generalSettings == null) {
			generalSettings = new GeneralSettings();
		}

		return generalSettings;
	}

	private static Integer getSizeMenu() {
		return getGeneralSettings().getSizeMenu();
	}

	private static Integer getSizeStandar() {
		return getGeneralSettings().getSizeStandar();
	}

	private static String getNombreFont() {
		return getGeneralSettings().getNombreFont();
	}

	private static String getSeparasorDecimal() {
		return getGeneralSettings().getSeparadorDecimal();
	}

	private static String getSeparasorMiles() {
		return getGeneralSettings().getSeparadorMiles();
	}

	// Funciones
	public static ImageIcon loadIcon(URL iconoURL) {
		return CommonUtils.loadIcon(iconoURL, 40, 40);
	}
	
	public static ImageIcon loadIconMenu(URL iconoURL) {
		return CommonUtils.loadIcon(iconoURL, 20, 20);
	}
	
	public static ImageIcon loadIcon(String nombrePicture) {
		return CommonUtils.loadIcon(nombrePicture, 40, 40);
	}

	public static ImageIcon loadIconMenu(String nombrePicture) {
		return CommonUtils.loadIcon(nombrePicture, 20, 20);
	}

	public static Font getStandarFontMenu() {
		return getStandarFont(getSizeMenu());
	}

	public static Font getStandarFont() {
		return getStandarFont(getSizeStandar());
	}

	public static Font getStandarFont(int size) {
		return new Font(getNombreFont(), Font.PLAIN, size);
	}

	public static Font getStandarFontItalic() {
		return new Font(getNombreFont(), Font.ITALIC, getSizeStandar());
	}

	public static Font getStandarFontItalic(int size) {
		return new Font(getNombreFont(), Font.ITALIC, size);
	}

	public static Font getStandarFontBold() {
		return getStandarFontBold(getSizeStandar());
	}

	public static Font getStandarFontBold(int size) {
		return new Font(getNombreFont(), Font.BOLD, size);
	}

	public static void SetFontToComponent(Component[] comp) {
		for (int x = 0; x < comp.length; x++) {
			if (comp[x] instanceof Container)
				SetFontToComponent(((Container) comp[x]).getComponents());
			try {
				comp[x].setFont(getStandarFont());
			} catch (Exception e) {
			} // do nothing
		}
	}

	public static Double String2Double(String valor) {
		// Determino de manera dinamica cual es el separador de miles y cual el de decimal
		String separadorMiles = getSeparasorMiles();

		int posPunto = valor.indexOf(".");
		int posComa = valor.indexOf(",");

		if (posComa == -1) {
			posComa = 99;
		}

		if (posPunto == -1) {
			posPunto = 99;
		}

		if ((posComa != 99) && (posPunto == 99)) {
			// Solo esta la coma
			separadorMiles = ".";
		} else {
			if ((posPunto != 99) && (posComa == 99)) {
				// Solo esta el punto
				separadorMiles = ",";
			} else {
				// Esta los dos, me fio el que esta a izquierda es el separador de miles

				if ((posPunto < posComa) && (posPunto < 3)) {
					separadorMiles = ".";
				}

				if ((posComa < posPunto) && (posComa < 3)) {
					separadorMiles = ",";
				}
			}
		}

		return CommonUtils.String2Double(valor, separadorMiles);
	}

	public static String double2String(Double valor) {
		return CommonUtils.double2String(valor, getSeparasorMiles(), getSeparasorDecimal());
	}

	public static List<String> getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		List<String> sb = new ArrayList<String>();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.add(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb;
	}

	public static Integer graphicSizeToInt(JPanel padre, String Texto) {
		return padre.getFontMetrics(getStandarFont()).stringWidth(Texto);
	}

	

}
