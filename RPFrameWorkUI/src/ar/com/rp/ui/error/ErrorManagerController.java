package ar.com.rp.ui.error;

import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.rpcutils.ExceptionUtils;
import ar.com.rp.ui.pantalla.BaseControllerDialog;
import ar.com.rp.ui.pantalla.BasePantallaPrincipal;

public class ErrorManagerController extends BaseControllerDialog<BasePantallaPrincipal<?, ?>, ErrorManagerView, ErrorManagerModel> {

	public enum mensage {
		OK, COPIAR
	};

	public ErrorManagerController(BasePantallaPrincipal<?, ?> pantPrincipal, ErrorManagerView view, ErrorManagerModel model) throws Exception {
		super(pantPrincipal, view, model, null);
		asignarPantallaPrin(pantPrincipal);
	};

	private void asignarPantallaPrin(BasePantallaPrincipal<?, ?> pantPrincipal) {
		if (pantPrincipal != null) {
			getView().btnCopiarTexto.setLoguerInterface(pantPrincipal.getLoguerInterface());
			getView().btnOK.setLoguerInterface(pantPrincipal.getLoguerInterface());
		}
	}

	@Override
	public void setPantallaPrincipal(BasePantallaPrincipal<?, ?> pantPrincipal) {
		super.setPantallaPrincipal(pantPrincipal);
		asignarPantallaPrin(pantPrincipal);
	}

	@Override
	protected String getNombrePantalla() {
		return "Mensage de Error";
	}

	@Override
	public void ejecuarAccion(String accion) {
		if (accion.equals(mensage.OK.toString())) {
			getView().setVisible(false);
		}

		if (accion.equals(mensage.COPIAR.toString())) {
			CommonUtils.copyToClipboard(getView().txtError.getText());
		}
	}

	@Override
	protected void cargaPantalla() throws Exception {
		getView().txtMsg.setText(getModel().getTitulo() + "\n\n" + getModel().getMensaje() + getModel().getUltError());

		if (getModel().getError() != null) {
			StringBuilder sb = new StringBuilder();

			if (getModel().getError().getMessage() != null) {
				sb.append(getModel().getError().getMessage());
				sb.append("\n");
				sb.append("\n");
				sb.append("/************** Detalle **************/\n");
			}

			// agrego el detalle
			sb.append(ExceptionUtils.exception2String(getModel().getError()));

			getView().txtError.setText(sb.toString());
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					getView().jsp.getVerticalScrollBar().setValue(0);
				}
			});
		} else {
			getView().txtError.setText("Error no detallado");
		}

		getView().setTitle(getModel().getTitulo());
	}

	@Override
	protected String getResultado() throws Exception {
		return "";
	}

}
