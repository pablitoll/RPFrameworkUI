package ar.com.rp.ui.error;

import java.util.List;

import com.google.common.base.Throwables;

import ar.com.rp.ui.pantalla.BaseModel;

public class ErrorManagerModel extends BaseModel {

	private Exception error;
	private String titulo;
	private String mensaje;

	public ErrorManagerModel(String titulo, String mensaje, Exception error) {
		super();
		this.titulo = titulo;
		this.mensaje = mensaje;
		this.error = error;
	}
	
	public void setError(Exception error) {
		this.error = error;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public ErrorManagerModel() {
		super();
	}
	
	public Exception getError() {
		return error;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getMensaje() {
		return mensaje.toString() + "\n";
	}

	public String getUltError() {
		if (getError() != null) {
			List<Throwable> listaError = Throwables.getCausalChain(getError());
			return listaError.get(listaError.size() - 1).getMessage();
		} else {
			return "";
		}
	}

}
