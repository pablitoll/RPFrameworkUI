package ar.com.rp.ui.exceptions;

public abstract class CSCExceptionBase extends Exception {
	
	private static final long serialVersionUID = 1L;

	public CSCExceptionBase(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public CSCExceptionBase(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CSCExceptionBase(String arg0) {
		super(arg0);
	}

	public CSCExceptionBase(Throwable arg0) {
		super(arg0);
	}

}
