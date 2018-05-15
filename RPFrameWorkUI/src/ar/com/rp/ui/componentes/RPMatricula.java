package ar.com.rp.ui.componentes;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

import ar.com.rp.ui.common.CommonRP;

public class RPMatricula extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RPMatricula() {
		addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();
				// Verificar si la tecla pulsada no es un digito
				if ((((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)
						&& (e.getKeyCode() != KeyEvent.VK_DELETE)))// ||
				{
					e.consume(); // ignorar el evento de teclado
				}
			}
		});

		addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent fe) {
				perdioFocus();
			}

			public void focusGained(FocusEvent fe) {
				ganoFocus();
			}
		});
	}
	
	@Override
	public void setText(String texto){
		texto = CommonRP.formatearMatricula(texto.replaceAll("-", ""));
		super.setText(texto);
	}

	protected void ganoFocus() {
		setText(getMatriculaSinFormato());
	}

	private void perdioFocus() {
		if(getText().length() > 1){
			setText(CommonRP.formatearMatricula(getText()));
		}
	}
	
	public String getMatriculaSinFormato() {
		return getText().replace("-", "").trim();
	}
	
	public String validar() throws Exception {
		String retorno = "";		
		if((getMatriculaSinFormato().length() > CommonRP.MatriculaLargoMaximo) || (getMatriculaSinFormato().length() < CommonRP.MatriculaLargoMinimo)){
			retorno = String.format("La Matricula debe tener entre %s y %s numeros", CommonRP.MatriculaLargoMinimo, CommonRP.MatriculaLargoMaximo);
		};
		
		if(retorno.equals("")){		
			//if(!Common.ValidarDV(Integer.valueOf(matricula))){
			if(!CommonRP.ValidarDV(getMatriculaSinFormato())){
				retorno = "El Digito verificador no es valido";
			}
		}
		
		return retorno;
	}
}
