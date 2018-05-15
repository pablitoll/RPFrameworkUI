package ar.com.rp.ui.error;

import ar.com.rp.ui.pantalla.BasePantallaPrincipal;

public class ErrorManager {
	
	private static ErrorManagerView view = null;
	private static ErrorManagerModel model = null;
	private static ErrorManagerController controller = null;

	public static void showError(BasePantallaPrincipal<?,?> pantPrincipal, String titulo, Exception error) throws Exception {
		showError(pantPrincipal, titulo, "", error);
	}
	
	public static void showError(BasePantallaPrincipal<?,?> pantPrincipal, String titulo, String mensaje, Exception error) throws Exception {

		if ((error != null) && (error.getMessage() != null)) {
			if (!mensaje.equals("")) {
				mensaje += "\n";
			}
			mensaje += error.getMessage();
		}

		if(view == null) {
			view = new ErrorManagerView();
			model = new ErrorManagerModel();
			controller = new ErrorManagerController(pantPrincipal, view, model);
		}
		
		model.setTitulo(titulo);
		model.setError(error);
		model.setMensaje(mensaje);
		controller.setPantallaPrincipal(pantPrincipal);
		
		controller.iniciar();
	}
}
