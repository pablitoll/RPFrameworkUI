package ar.com.rp.ui.pantalla;

import java.util.ArrayList;

public class ControllerManager {

	private ArrayList<registroControllerManager> listaController;

	public ControllerManager() {
		listaController = new ArrayList<registroControllerManager>();
	}

	public void add(BaseControllerMVC<?, ?, ?> controller, String identificador) {
		registroControllerManager aux = new registroControllerManager(controller, identificador);
		listaController.add(aux);
	}

	public BaseControllerMVC<?, ?, ?> findOrCreated(String identificador) throws Exception {
		boolean encontro = false;
		for (int i = 0; (i < listaController.size()) && !encontro; i++) {
			if (identificador.equalsIgnoreCase(listaController.get(i).getIdentificador())) {
				registroControllerManager aux = listaController.get(i);
				listaController.remove(i); // lo paso a la ultima posicion
				listaController.add(aux);
				encontro = true;
				aux.getController().findOrCreated();

				return aux.getController();
			}
		}

		return null;
	}

	public BaseControllerMVC<?, ?, ?> findControler(String identificador) {
		BaseControllerMVC<?, ?, ?> retorno = null;
		for (int i = 0; i < listaController.size(); i++) {
			if (identificador.equalsIgnoreCase(listaController.get(i).getIdentificador())) {
				retorno = listaController.get(i).getController();
			}
		}
		return retorno;
	}

	public void focusLastCall() throws Exception {
		boolean fin = false;
		for (int i = listaController.size() - 1; (i >= 0) && !fin; i--) {
			if (listaController.get(i).getController().isActivo()) {
				fin = true;
				listaController.get(i).getController().findOrCreated();
			}
		}
	}

	public BaseControllerMVC<?, ?, ?> getLastCall() {
		BaseControllerMVC<?, ?, ?> retorno = null;
		boolean fin = false;
		for (int i = listaController.size() - 1; (i >= 0) && !fin; i--) {
			if (listaController.get(i).getController().isActivo()) {
				fin = true;
				retorno = listaController.get(i).getController();
			}
		}
		return retorno;
	}

	public boolean isAlreadyCreated(String identificador) {
		boolean retorno = false;
		for (int i = 0; i < listaController.size(); i++) {
			if (identificador.equalsIgnoreCase(listaController.get(i).getIdentificador())) {
				retorno = true;
			}
		}

		return retorno;
	}

	public BaseControllerMVC<?, ?, ?> getBaseController(String identificador) {
		BaseControllerMVC<?, ?, ?> retorno = null;
		for (int i = 0; i < listaController.size(); i++) {
			if (identificador.equalsIgnoreCase(listaController.get(i).getIdentificador())) {
				retorno = listaController.get(i).getController();
			}
		}

		return retorno;
	}

//	public List<BaseControllerMVC> getListaVentanas() {
//		List<BaseControllerMVC> retorno = new ArrayList<BaseControllerMVC>();
//		for (registroControllerManager aux : listaController) {
//			retorno.add(aux.getController());
//		}
//
//		return retorno;
//	}

	public void refreshPermisos() throws Exception {
		for (registroControllerManager ventana : listaController) {
			ventana.getController().cargarPermisos();
		}
	}
}

class registroControllerManager {
	private BaseControllerMVC<?, ?, ?> controller;
	private String identificador;

	public BaseControllerMVC<?, ?, ?> getController() {
		return controller;
	}

	public void setController(BaseControllerMVC<?, ?, ?> controller) {
		this.controller = controller;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public registroControllerManager(BaseControllerMVC<?, ?, ?> controller, String identificador) {
		super();
		this.controller = controller;
		this.identificador = identificador;
	}
}