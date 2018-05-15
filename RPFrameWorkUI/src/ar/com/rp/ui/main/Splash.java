package ar.com.rp.ui.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.SplashScreen;

//Para que aparezca el splash se debe correr:
//java -splash:resources/escudo-azul.gif -jar CSCCliente.jar
public class Splash {

	private SplashScreen splash = null;
	private Graphics2D graphisSplash = null;

	private void renderSplashFrame(String msg) {
		graphisSplash.setComposite(AlphaComposite.Clear);
		graphisSplash.fillRect(20, 130, 300, 40);
		graphisSplash.setPaintMode();
		graphisSplash.setColor(Color.WHITE);
		graphisSplash.drawString(msg + "...", 20, 140);
	}

	public Splash() {
		inicializar();
	}

	private void inicializar() {
		splash = SplashScreen.getSplashScreen();
		if (splash == null) {
			System.out.println("SplashScreen.getSplashScreen() returned null");
			return;
		}
		graphisSplash = splash.createGraphics();
		if (graphisSplash == null) {
			System.out.println("graphisSplash is null");
			return;
		}
		renderSplashFrame("");

	}

	public void refreshSplash(String msg) {
		if ((graphisSplash != null) && (splash != null)) {
			renderSplashFrame(msg);
			try {
				splash.update();
			} catch (Exception e) {
				if (e.getMessage().contains("no splash screen available")) {
					// Lo vuelvo a generar porque despues del Dialog, se "pierde" el Splash
					inicializar();
					refreshSplash(msg);
				}
			}

		};
		System.out.println(msg);
	}
}