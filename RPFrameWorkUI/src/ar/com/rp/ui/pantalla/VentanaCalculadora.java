package ar.com.rp.ui.pantalla;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.common.FrameworkCommon;
import ar.com.rp.ui.main.MainFramework;

public class VentanaCalculadora extends JFrame implements KeyListener {
	/**
	 * generado
	 */
	private static final long serialVersionUID = 1583724102189855698L;

	private int estado = 0;
	private BasePantallaPrincipalView pantallaPrincipal = null;

	private enum operaciones {
		NUMERO, OPERACION, PUNTO, DELETE, BACK, IGUAL, COPY, NEGATIVO
	};

	//@formatter:off
	private enum botones {
		n7("7", "", true, operaciones.NUMERO, new int[] {KeyEvent.VK_7, KeyEvent.VK_NUMPAD7}),		
		n8("8", "", true, operaciones.NUMERO, new int[] {KeyEvent.VK_8, KeyEvent.VK_NUMPAD8}), 
		n9("9", "", true, operaciones.NUMERO, new int[] {KeyEvent.VK_9, KeyEvent.VK_NUMPAD9}),
		n4("4", "", true, operaciones.NUMERO, new int[] {KeyEvent.VK_4, KeyEvent.VK_NUMPAD4}),
		n5("5", "", true, operaciones.NUMERO, new int[] {KeyEvent.VK_5, KeyEvent.VK_NUMPAD5}),
		n6("6", "", true, operaciones.NUMERO, new int[] {KeyEvent.VK_6, KeyEvent.VK_NUMPAD6}),
		n1("1", "", true, operaciones.NUMERO, new int[] {KeyEvent.VK_1, KeyEvent.VK_NUMPAD1}),
		n2("2", "", true, operaciones.NUMERO, new int[] {KeyEvent.VK_2, KeyEvent.VK_NUMPAD2}),
		n3("3", "", true, operaciones.NUMERO, new int[] {KeyEvent.VK_3, KeyEvent.VK_NUMPAD3}),
		n0("0", "", true, operaciones.NUMERO, new int[] {KeyEvent.VK_0, KeyEvent.VK_NUMPAD0}),
		punto(".", "", true, operaciones.PUNTO, new int[] {KeyEvent.VK_PERIOD, KeyEvent.VK_DECIMAL}), 
		M("M", "presiones M o haga click", true, operaciones.COPY, KeyEvent.VK_M), 
		CE("CE", "presiones SUPR o haga click", false, operaciones.DELETE, KeyEvent.VK_DELETE), 
		BORRARNUMERO("<<<", "", false, operaciones.BACK, KeyEvent.VK_BACK_SPACE), 
		NEGATIVO("+/-", "presiones '-' del teclado alfanumerico", false, operaciones.NEGATIVO, KeyEvent.VK_MINUS), 
		MULTIPLICAR("*", "", false, operaciones.OPERACION, KeyEvent.VK_MULTIPLY), 
		DIVIDIR("/", "", false, operaciones.OPERACION, new int[] {KeyEvent.VK_SLASH, 111}), 
		SUMAR("+", "", false, operaciones.OPERACION, new int[] {KeyEvent.VK_PLUS, 107}), 
		RESTAR("-", "", false, operaciones.OPERACION, KeyEvent.VK_SUBTRACT), 
		IGUAL("=", "", false, operaciones.IGUAL, KeyEvent.VK_ENTER);
		
		//@formatter:on		

		private String caption;
		private String leyenda;
		private boolean panelNumero;
		private operaciones operacion;
		private int[] keyCode;

		private botones(String caption, String leyenda, boolean panelNumero, operaciones operacion, int[] keyCode) {
			this.leyenda = leyenda;
			this.operacion = operacion;
			this.panelNumero = panelNumero;
			this.caption = caption;
			this.keyCode = keyCode;
		}

		private botones(String caption, String leyenda, boolean panelNumero, operaciones operacion, int keyCode) {
			this.leyenda = leyenda;
			this.operacion = operacion;
			this.panelNumero = panelNumero;
			this.caption = caption;
			this.keyCode = new int[] { keyCode };
		}

		public String getCaption() {
			return caption;
		}

		public String getLeyenda() {
			return leyenda;
		}

		public boolean isPanelNumero() {
			return panelNumero;
		}

		public operaciones getOperacion() {
			return operacion;
		}

		public int[] getKeyCode() {
			return keyCode;
		}
	}

	private boolean primeroCaracter = true;

	private static String header = "<div align='right'><font size='6' face='Arial'>";
	private static String foot = "</font></div>";

	/** numero tecleado */
	JTextPane pantalla;

	/** guarda el resultado de la operacion anterior o el número tecleado */
	double resultado;

	/** para guardar la operacion a realizar */
	// boolean ingresoOperacion = false;
	botones operacionIngresada = null;
	String historial = "";
	String linea = "";

	/** Los paneles donde colocaremos los botones */
	JPanel panelNumeros, panelOperaciones;

	/**
	 * Constructor. Crea los botones y componentes de la calculadora
	 */
	public VentanaCalculadora(String icono, BasePantallaPrincipalView pantallaPrincipal) {
		super();
		this.pantallaPrincipal = pantallaPrincipal;
		primeroCaracter = true;
		MainFramework.setUIFont(Common.getStandarFont());
		setSize(250, 853);
		addKeyListener(this);

		setTitle("Calculadora");
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setResizable(false);

		// Vamos a dibujar sobre el panel
		JPanel panel = (JPanel) this.getContentPane();
		panel.setLayout(new BorderLayout());
		panel.setFocusable(false);

		pantalla = new JTextPane();
		pantalla.setEditable(false);
		pantalla.setContentType("text/html");
		pantalla.setBorder(new EmptyBorder(4, 4, 4, 4));
		pantalla.setBackground(Color.WHITE);
		pantalla.setFocusable(false);

		JScrollPane scroll = new JScrollPane(pantalla);
		panel.add(scroll, BorderLayout.CENTER);
		scroll.setBounds(pantalla.getBounds());
		scroll.setPreferredSize(new Dimension(6, 600));
		scroll.setVisible(true);

		panelNumeros = new JPanel();
		panelNumeros.setLayout(new GridLayout(4, 3));
		panelNumeros.setBorder(new EmptyBorder(4, 4, 4, 4));
		panelNumeros.setFocusable(false);
		panelNumeros.setFocusable(false);

		panelOperaciones = new JPanel();
		panelOperaciones.setLayout(new GridLayout(8, 1));
		panelOperaciones.setBorder(new EmptyBorder(4, 4, 4, 4));
		panelOperaciones.setFocusable(false);
		panelOperaciones.setFocusable(false);

		for (botones bot : botones.values()) {
			nuevoBoton(bot);
		}

		JPanel pnlBotones = new JPanel();
		pnlBotones.setBorder(null);
		pnlBotones.setLayout(new BorderLayout());
		pnlBotones.setFocusable(false);

		pnlBotones.add(panelOperaciones, BorderLayout.EAST);
		pnlBotones.add(panelNumeros, BorderLayout.CENTER);

		panel.add(pnlBotones, BorderLayout.SOUTH);

		pantalla.setVisible(true);
		validate();
		if (icono != null) {
			this.setIconImage(CommonUtils.loadImage(icono, 80, 80));
		}
	}

	public void visualizar(JDesktopPane desktop) {
		FrameworkCommon.ponerADerecha(this, 250, 853, pantallaPrincipal);
		this.setVisible(true);
	}

	/**
	 * Crea un boton del teclado numérico y enlaza sus eventos con el listener correspondiente
	 * 
	 * @param digito
	 *            boton a crear
	 */

	private void nuevoBoton(botones boton) {
		JButton btn = new JButton(boton.getCaption());

		btn.setFocusable(false);

		if (!boton.getLeyenda().equals("")) {
			btn.setToolTipText(boton.getLeyenda());
		}

		btn.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent evt) {
				pulsoBoton(boton);
			}
		});

		btn.addKeyListener(this);

		if (boton.getOperacion() != operaciones.NUMERO) {
			btn.setForeground(Color.RED);
		}

		if (boton.isPanelNumero()) {
			panelNumeros.add(btn);
		} else {
			panelOperaciones.add(btn);
		}
	}

	private String formatearNumero(String numero) {
		String negativo = "";
		if (numero.indexOf("-") >= 0) {
			numero = numero.replace("-", "");
			negativo = "-";
		}

		int posPunto = numero.indexOf(Common.getGeneralSettings().getSeparadorDecimal());
		if (posPunto < 0) {
			posPunto = numero.indexOf(Common.getGeneralSettings().getSeparadorMiles());
		}
		String decimal = "";
		if (posPunto >= 0) {
			decimal = numero.substring(posPunto, numero.length());
			numero = numero.substring(0, posPunto);
		}

		if (numero.length() > 3) {
			return negativo + formatearNumero(numero.substring(0, numero.length() - 3)) + Common.getGeneralSettings().getSeparadorMiles() + CommonUtils.strRigth(numero, 3)
					+ decimal;
		} else {
			return negativo + numero + decimal;
		}
	}

	private void dibujar(String texto) {
		String separador = "<br>";
		if (primeroCaracter) {
			separador = "";
		}
		pantalla.setText(header + historial + separador + formatearNumero(texto) + foot);
	}

	private void dibujarHistory(String texto, boolean esResultado) {
		if (!texto.equals("")) {
			String org = texto.replace(",", ".");
			texto = formatearNumero(texto);
			if (esResultado) {
				if (new Double(org) >= 0) {
					texto = CommonUtils.SetHTMLColor(texto, "blue", false);
				} else {
					texto = CommonUtils.SetHTMLColor(texto, "red", false);
				}
			}
			String separador = "<br>";
			if (primeroCaracter) {
				separador = "";
				primeroCaracter = false;
			}

			historial += separador + texto;
			pantalla.setText(header + historial + foot);
		}
		;
	}

	private void pulsoBoton(botones boton) {
		switch (estado) {
			case 0:
				estado0(boton);
				break;
			case 1:
				estado1(boton);
				break;
			case 2:
				estado2(boton);
				break;
			case 3:
				estado3(boton);
				break;
			case 4:
				estado4(boton);
				break;
			case 5:
				estado5(boton);
				break;
		}
	}

	private void estado0(botones boton) {
		if (boton.getOperacion() == operaciones.NUMERO) {
			linea += boton.getCaption();
			dibujar(linea);
			estado = 1;
		}
	}

	private void estado1(botones boton) {
		if (boton.getOperacion() == operaciones.NUMERO) {
			linea += boton.getCaption();
			dibujar(linea);
		}

		if (boton.getOperacion() == operaciones.PUNTO) {
			linea += ".";
			dibujar(linea);
			estado = 2;
		}

		if (boton.getOperacion() == operaciones.DELETE) {
			opereacionDelete();
			dibujar(linea);
		}

		if (boton.getOperacion() == operaciones.IGUAL) {
			operacionPulsado(boton);
			estado = 5;
		}

		if (boton.getOperacion() == operaciones.OPERACION) {
			operacionPulsado(boton);
			estado = 4;
		}

		if (boton.getOperacion() == operaciones.NEGATIVO) {
			opereacionNegativo();
		}

		if (boton.getOperacion() == operaciones.BACK) {
			opereacionBorrarNumero();
		}

		if (boton.getOperacion() == operaciones.COPY) {
			opereacionCopyLinea();
		}
	}

	private void estado2(botones boton) {
		if (boton.getOperacion() == operaciones.NUMERO) {
			linea += boton.getCaption();
			dibujar(linea);
			estado = 3;
		}

		if (boton.getOperacion() == operaciones.DELETE) {
			opereacionDelete();
			dibujar(linea);
			estado = 0;
		}

		if (boton.getOperacion() == operaciones.NEGATIVO) {
			opereacionNegativo();
		}

		if (boton.getOperacion() == operaciones.BACK) {
			opereacionBorrarNumero();
			estado = 1;
		}
	}

	private void estado3(botones boton) {
		if (boton.getOperacion() == operaciones.NUMERO) {
			linea += boton.getCaption();
			dibujar(linea);
		}

		if (boton.getOperacion() == operaciones.OPERACION) {
			operacionPulsado(boton);
			estado = 4;
		}

		if (boton.getOperacion() == operaciones.DELETE) {
			opereacionDelete();
			dibujar(linea);
			estado = 0;
		}

		if (boton.getOperacion() == operaciones.IGUAL) {
			operacionPulsado(boton);
			estado = 5;
		}

		if (boton.getOperacion() == operaciones.NEGATIVO) {
			opereacionNegativo();
		}

		if (boton.getOperacion() == operaciones.BACK) {
			opereacionBorrarNumero();
			if (linea.substring(linea.length() - 1).equals(Common.getGeneralSettings().getSeparadorDecimal())) {
				estado = 2;
			}
		}

		if (boton.getOperacion() == operaciones.COPY) {
			opereacionCopyLinea();
		}
	}

	private void estado4(botones boton) {
		if (boton.getOperacion() == operaciones.NUMERO) {
			linea += boton.getCaption();
			dibujar(linea);
			estado = 1;
		}

		if (boton.getOperacion() == operaciones.DELETE) {
			opereacionDelete();
			dibujar(linea);
			estado = 0;
		}

		if (boton.getOperacion() == operaciones.NEGATIVO) {
			opereacionNegativo();
		}
	}

	private void estado5(botones boton) {
		if (boton.getOperacion() == operaciones.NUMERO) {
			insertarLinea();
			linea = boton.getCaption();
			dibujar(linea);
			estado = 1;
		}

		if (boton.getOperacion() == operaciones.DELETE) {
			opereacionDelete();
			dibujar(linea);
			estado = 0;
		}

		if (boton.getOperacion() == operaciones.OPERACION) {
			operacionPulsado(boton);
			estado = 4;
		}

		if (boton.getOperacion() == operaciones.COPY) {
			opereacionCopyResultado();
		}
	}

	private void opereacionDelete() {
		resultado = 0;

		pantalla.setText("");
		historial = "";
		linea = "";
		operacionIngresada = null;
	}

	private void opereacionCopyResultado() {
		CommonUtils.copyToClipboard(Common.double2String(resultado));
	}

	private void opereacionCopyLinea() {
		CommonUtils.copyToClipboard(linea);
	}

	private void opereacionNegativo() {
		if ((linea.length() > 0) && linea.substring(0, 1).equals("-")) {
			linea = linea.substring(1, linea.length());
		} else {
			linea = "-" + linea;
		}
		dibujar(linea);
	}

	private void opereacionBorrarNumero() {
		if (!linea.equals("")) {
			linea = linea.substring(0, linea.length() - 1);
			dibujar(linea);
		}
	}

	private void operacionPulsado(botones tecla) {
		if ((operacionIngresada != botones.IGUAL) && ((tecla == botones.DIVIDIR) || (tecla == botones.MULTIPLICAR))) {
			operacionPulsado(botones.IGUAL);
		}

		dibujarHistory(linea, false);
		dibujarHistory(tecla.getCaption(), false);
		calcularResultado(operacionIngresada);
		operacionIngresada = tecla;
		linea = "";
		if (tecla == botones.IGUAL) {
			dibujarHistory(Common.double2String(resultado), true);
		}
	}

	/**
	 * Calcula el resultado y lo muestra por pantalla
	 */
	private void calcularResultado(botones operacion) {
		if (!linea.equals("")) {
			if (operacion == botones.SUMAR) {
				resultado += new Double(linea);
			} else if (operacion == botones.RESTAR) {
				resultado -= new Double(linea);
			} else if (operacion == botones.DIVIDIR) {
				resultado /= new Double(linea);
			} else if (operacion == botones.MULTIPLICAR) {
				resultado *= new Double(linea);
			} else {
				resultado = new Double(linea);
			}
		}
	}

	private void salir() {
		this.setVisible(false);
	}

	private void insertarLinea() {
		if (!linea.equals("")) {
			dibujarHistory(linea, false);
		}
		linea = "";
		historial += foot + "<hr align='right' width='230'size = '3' noshade>" + header;
		primeroCaracter = true;
		pantalla.setText(header + historial + foot);
	}

	@Override
	public void keyPressed(KeyEvent ke) {

		if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
			salir();
		}

		for (botones btn : botones.values()) {
			for (int codigo : btn.getKeyCode()) {
				if (codigo == ke.getKeyCode()) {
					pulsoBoton(btn);
				}
			}
		}
		ke.consume();
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		ke.consume();
	}

	@Override
	public void keyTyped(KeyEvent ke) {
		ke.consume();
	}
}