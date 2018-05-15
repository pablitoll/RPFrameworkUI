package ar.com.rp.ui.componentes;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.SwingConstants;

import ar.com.rp.ui.common.Common;

public class JButtonBarraBotonesRP extends JButtonRP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6473446029300808271L;
	private String textOriginal = null;
	private Integer anchoBarra = null;
	private Integer margenScrollBarra = null;
	private Integer altoIcono = 40;

	public Integer getAnchoBarra() {
		return anchoBarra;
	}

	public void setAnchoBarra(Integer anchoBarra) {
		this.anchoBarra = anchoBarra;
		setDimensiones();
	}

	public Integer getMargenScrollBarra() {
		return margenScrollBarra;
	}

	public void setMargenScrollBarra(Integer margenScrollBarra) {
		this.margenScrollBarra = margenScrollBarra;
		setDimensiones();
	}

	public JButtonBarraBotonesRP(String caption) {
		super(caption);
		seteosGenericos();
		setText(caption);
	}

	public JButtonBarraBotonesRP(String caption, String nombreBoton) {
		super(caption, nombreBoton);
		seteosGenericos();
		setText(caption);
	}

	private void seteosGenericos() {

		setFocusable(false);

		setVerticalTextPosition(SwingConstants.BOTTOM);
		setHorizontalTextPosition(SwingConstants.CENTER);

		setContentAreaFilled(false);
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				setContentAreaFilled(true);
			}

			public void mouseExited(MouseEvent evt) {
				setContentAreaFilled(false);
			}

		});
		
		setFont(Common.getStandarFont(Common.getGeneralSettings().getSizeBotonBarra()));
	}

	public void setDimensiones() {
		if ((anchoBarra != null) && (margenScrollBarra != null) && (textOriginal != null)) {

			int sizeAltoIcono = 0;
			if (getIcon() != null) {
				sizeAltoIcono = altoIcono;
			}

			FontMetrics fontMetrics = getFontMetrics(getFont());
			int altoBotonBarra = sizeAltoIcono + fontMetrics.getHeight() + 20; // 40 del icono + font + margen de 20
			setPreferredSize(new Dimension(anchoBarra - margenScrollBarra, altoBotonBarra));
			setSize(anchoBarra - margenScrollBarra, altoBotonBarra);

			int anchoTexto = fontMetrics.stringWidth(textOriginal);
			if (anchoTexto > anchoBarra - margenScrollBarra) {
				setPreferredSize(new Dimension(anchoBarra - margenScrollBarra, altoBotonBarra + fontMetrics.getHeight()));
				setSize(anchoBarra - margenScrollBarra, altoBotonBarra + fontMetrics.getHeight());
			}
		}
	}

	@Override
	public void setText(String text) {
		textOriginal = text.trim();
		super.setText(String.format("<html><center>%s</center></html>", textOriginal));
		setDimensiones();
	}

	@Override
	public void setIcon(Icon defaultIcon) {
		super.setIcon(defaultIcon);
		setDimensiones();
	}
	

}
