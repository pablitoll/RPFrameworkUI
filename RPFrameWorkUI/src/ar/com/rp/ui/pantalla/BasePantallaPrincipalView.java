package ar.com.rp.ui.pantalla;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowListener;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;

import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.JButtonBarraBotonesRP;
import ar.com.rp.ui.componentes.ManejoPantalla;
import ar.com.rp.ui.interfaces.ManejoPantallaCall;

public abstract class BasePantallaPrincipalView extends JFrame implements ManejoPantallaCall {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int margenScrollBarra = 30;
	private int anchoBarra = 130;
	private int altoBarra = 800;

	private ManejoPantalla manejoPantalla = new ManejoPantalla();

	public JMenuBar menuBar = new JMenuBar();

	public JPanel toolBarBotones = new JPanel();
	public JPanel pnlBotton = new JPanel();
	public JScrollPane sbToolBarBotones = new JScrollPane(toolBarBotones);
	public JToolBar toolBarSuperior = new JToolBar("Accesos Directos a Panel Superior");

	public JDesktopPane desktopPane;
	private final JPanel pnlHeader = new JPanel();

	public BasePantallaPrincipalView(ImageIcon imgFondo) {
		sbToolBarBotones.setName("Barra Botones Laterales");
		menuBar.setName("Barra de Menu");
		menuBar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0), "none");
		desktopPane = new JDesktopPane() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			private ImageIcon imgagenFondo = imgFondo;
			private Image newimage;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (imgagenFondo != null) {
					if (newimage == null) {
						newimage = imgagenFondo.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
					}

					g.drawImage(newimage, (this.getWidth() - 500) / 2, (this.getHeight() - 500) / 2, this);
					refrescarBarraLateral();
				}
			}
		};

		setMinimumSize(new Dimension(1100, 900));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		pnlBotton.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlBotton.setMinimumSize(new Dimension(10, 10));
		pnlBotton.setLayout(new BorderLayout(0, 0));

		getContentPane().add(pnlBotton, BorderLayout.SOUTH);

		toolBarBotones.setRequestFocusEnabled(false);
		toolBarBotones.setPreferredSize(new Dimension(anchoBarra - margenScrollBarra, altoBarra));
		toolBarBotones.setFocusable(false);
		toolBarBotones.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent mouseWell) {
				int valor = (mouseWell.getWheelRotation() * mouseWell.getScrollAmount() * 40)
						+ sbToolBarBotones.getVerticalScrollBar().getValue();
				sbToolBarBotones.getVerticalScrollBar().setValue(valor);
			}
		});

		toolBarBotones.setBorder(null);
		toolBarBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		sbToolBarBotones.setPreferredSize(new Dimension(anchoBarra, altoBarra));

		getContentPane().add(sbToolBarBotones, BorderLayout.WEST);

		getContentPane().add(desktopPane, BorderLayout.CENTER);
		desktopPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		getContentPane().add(pnlHeader, BorderLayout.NORTH);
		pnlHeader.setLayout(new BorderLayout(0, 0));
		pnlHeader.add(toolBarSuperior, BorderLayout.CENTER);

		toolBarSuperior.setMargin(new Insets(5, 10, 5, 10));
		pnlHeader.add(menuBar, BorderLayout.NORTH);
	}

	public void asignarBotonAccion(AbstractButton boton, String accion) {
		manejoPantalla.asignarBotonAccion(boton, accion);
	}

	public void setActionListener(ActionListener actionListener) {
		manejoPantalla.setActionListener(actionListener);
	}

	public void initialize() {
		setVisible(true);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

	private void refrescarBarraLateral() {
		if (toolBarBotones.isVisible()) {
			Integer max = 0;
			for (int i = 0; i < toolBarBotones.getComponentCount(); i++) {
				Component comp = toolBarBotones.getComponents()[i];
				if (comp.getHeight() + comp.getLocation().getY() > max) {
					max = (int) (comp.getHeight() + comp.getLocation().getY());
				}
			}

			if (max != toolBarBotones.getPreferredSize().getWidth()) {
				toolBarBotones.setPreferredSize(new Dimension(anchoBarra - margenScrollBarra, max));
				sbToolBarBotones.setPreferredSize(new Dimension(anchoBarra, max));
			}
		}

	}

	public int getAnchoBarra() {
		return anchoBarra;
	}

	public void setAnchoBarra(int anchoBarra) {
		this.anchoBarra = anchoBarra;
	}

	public void eventoWindowListener(WindowListener wl) {
		addWindowListener(wl);
	}

	protected void agregarBotonStd2Barra(JButtonBarraBotonesRP boton, URL iconoURL) {
		boton.setMargenScrollBarra(margenScrollBarra);
		boton.setAnchoBarra(anchoBarra);

		if (iconoURL != null) {
			boton.setIcon(Common.loadIcon(iconoURL));

		}
		toolBarBotones.add(boton);
	}

	protected void agregarBotonStd2Barra(JButtonBarraBotonesRP boton) {
		boton.setMargenScrollBarra(margenScrollBarra);
		boton.setAnchoBarra(anchoBarra);

		toolBarBotones.add(boton);
	}
	protected void agregarBotonStd2Barra(JButtonBarraBotonesRP boton, String icono) {
		boton.setMargenScrollBarra(margenScrollBarra);
		boton.setAnchoBarra(anchoBarra);

		if (icono != null) {
			boton.setIcon(Common.loadIcon(icono));
		}
		toolBarBotones.add(boton);
	}
}
