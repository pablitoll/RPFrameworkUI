package ar.com.rp.ui.componentes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.ui.exceptions.CSCPropiedadNoDefinida;

public class ArchivoPropiedades<TipoPropiedades> {

	/**
	 * 
	 */
	private Properties pRecurso = null;
	private String nombreArchivo = null;

	public ArchivoPropiedades(InputStream inputStream) throws Exception {
		loadStream(inputStream);
	}

	public ArchivoPropiedades(String nombreArchivo) throws Exception {
		if (CommonUtils.existeArchivo(nombreArchivo)) {
			this.nombreArchivo = nombreArchivo;
			loadStream(new FileInputStream(nombreArchivo));
		} else {
			Exception error = new Exception("No se encontro el archivo: " + nombreArchivo);
			error.printStackTrace();
			throw error;
		}
	}

	private void loadStream(InputStream inputStream) throws Exception {
		pRecurso = new Properties();
		pRecurso.load(inputStream);
	}

	public String getPropiedad(TipoPropiedades propiedad, String valorDefault) throws Exception {
		String aux = pRecurso.getProperty(propiedad.toString());
		if (aux == null) {
			if (valorDefault == null) {
				CSCPropiedadNoDefinida error = new CSCPropiedadNoDefinida("No esta configurada la propiedad: " + propiedad);
				error.printStackTrace();
				throw error;
			} else {
				aux = valorDefault;
				pRecurso.setProperty(propiedad.toString(), valorDefault);
			}
		}

		return aux;
	}

	public String getPropiedad(TipoPropiedades propiedad) throws Exception {
		return getPropiedad(propiedad, null);
	}

	public void setProperty(TipoPropiedades propiedad, String valor) throws Exception {
		if (nombreArchivo == null) {
			Exception error = new Exception("No se puede guardar una propiedad de un recurso que fue abierto por InputStream");
			error.printStackTrace();
			throw error;
		} else {
			try {
				pRecurso.setProperty(propiedad.toString(), valor);

				File f = new File(nombreArchivo);
				OutputStream out = new FileOutputStream(f);
				pRecurso.store(out, "");

			} catch (IOException e) {
				Exception error = new Exception("Error al escribir archivo de configuracion " + propiedad.toString(), e);
				error.printStackTrace();
				throw error;
			}
		}
	}

}
