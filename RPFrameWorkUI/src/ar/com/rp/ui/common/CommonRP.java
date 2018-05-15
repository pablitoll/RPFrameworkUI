package ar.com.rp.ui.common;

import ar.com.rp.rpcutils.CommonUtils;

public class CommonRP {

	public static Integer MatriculaLargoMinimo = 6; // incluye DV
	public static Integer MatriculaLargoMaximo = 7; // incluye DV

	public static Integer CuentaLargoMinimo() {
		return MatriculaLargoMinimo + 3;
	}

	public static Integer CuentaLargoMaximo() {
		return MatriculaLargoMaximo + 3;
	}

	public static boolean ValidarDV(String matricula) throws Exception {
		boolean retorno = false;
		if ((matricula.length() >= MatriculaLargoMinimo) && (matricula.length() <= MatriculaLargoMaximo)) {
			matricula = CommonUtils.PadLeft(matricula, MatriculaLargoMaximo, "0");

			Integer n1 = Integer.valueOf(matricula.substring(0, 1));
			Integer n2 = Integer.valueOf(matricula.substring(1, 2));
			Integer n3 = Integer.valueOf(matricula.substring(2, 3));
			Integer n4 = Integer.valueOf(matricula.substring(3, 4));
			Integer n5 = Integer.valueOf(matricula.substring(4, 5));
			Integer n6 = Integer.valueOf(matricula.substring(5, 6));

			Integer df = ((n1 * 9) + (n2 * 7) + n3 + (n4 * 3) + (n5 * 9) + (n6 * 7)) % 10;

			retorno = (Integer.valueOf(matricula.substring(6, 7)) == df);
		}
		return retorno;
	}

	public static String formatearMatricula(String nroMatricula) {
		if (nroMatricula.length() >= MatriculaLargoMinimo) {
			return nroMatricula.substring(0, nroMatricula.length() - 1) + "-" + nroMatricula.substring(nroMatricula.length() - 1, nroMatricula.length());
		} else {
			return nroMatricula;
		}
	}

	public static String cuentaToMatricula(String cuenta) {
		// Basicamente le saco los tres digitos de la cuenta y me queda la matricula
		String matricula = "";
		if (cuenta.length() >= CuentaLargoMinimo()) {
			matricula = cuenta.substring(0, cuenta.length() - 3);
		}

		return matricula;
	}

	public static String cuentaToSoloCuenta(String cuenta) {
		String soloCuenta = "";
		if (cuenta.length() >= 4) { // Le saco los 3 numero del numero de cuenta
			soloCuenta = cuenta.substring((cuenta.length() - 3), cuenta.length());
		}

		return soloCuenta;
	}

}
