package ar.com.rp.ui.error;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.FocusTraversalOnArray;
import ar.com.rp.ui.componentes.JButtonRP;
import ar.com.rp.ui.error.ErrorManagerController.mensage;
import ar.com.rp.ui.pantalla.BaseViewDialog;

public class ErrorManagerView extends BaseViewDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 421664725433502761L;
	private final JPanel contentPanel = new JPanel();
	public JTextPane txtError = new JTextPane();
	public JTextPane txtMsg = new JTextPane();
	public JScrollPane jsp = new JScrollPane(txtError);
	public JButtonRP btnOK = new JButtonRP("OK");
	public JButtonRP btnCopiarTexto = new JButtonRP("Copiar Texto");

	public ErrorManagerView() throws Exception {
		super();
		setAlwaysOnTop(true);
		setMinimumSize(new Dimension(650, 380));
		setFont(Common.getStandarFont());
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 659, 379);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setFocusable(false);
			tabbedPane.setFont(Common.getStandarFont());
			contentPanel.add(tabbedPane);

			JPanel pnlError = new JPanel();
			pnlError.setFont(Common.getStandarFont());
			tabbedPane.addTab("Error", null, pnlError, null);
			pnlError.setLayout(new BorderLayout(0, 0));
			{
				txtMsg.setFocusable(false);
				txtMsg.setRequestFocusEnabled(false);
				txtMsg.setEditable(false);
				pnlError.add(txtMsg);
			}

			JPanel pnlDetalle = new JPanel();
			tabbedPane.addTab("Detalle", null, pnlDetalle, null);
			pnlDetalle.setLayout(new BorderLayout(0, 0));
			{
				txtError.setFocusable(false);
				txtError.setRequestFocusEnabled(false);
				txtError.setEditable(false);
				pnlDetalle.add(jsp);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			btnOK.setMnemonic(KeyEvent.VK_ESCAPE);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnCopiarTexto.setFont(Common.getStandarFont());
				buttonPane.add(btnCopiarTexto);
				btnOK.setFont(Common.getStandarFont());
				buttonPane.add(btnOK);

			}
			buttonPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { btnOK, btnCopiarTexto }));
		}
	}

	@Override
	public void asignarBotones() {
		asignarBotonAccion(btnOK, mensage.OK.toString());
		asignarBotonAccion(btnCopiarTexto, mensage.COPIAR.toString());
	}
}
