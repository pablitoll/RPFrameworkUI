package ar.com.rp.ui.componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import ar.com.rp.rpcutils.FechaManagerUtil;
import ar.com.rp.ui.common.Common;

public class DatePickerCSC extends JPanel implements JDatePicker {

	private static final long serialVersionUID = 2814777654384974503L;

	private Popup popup;
	private JFormattedTextField formattedTextField;
	private JButton button;

	private JDatePanelImpl datePanel;
	private InternalEventHandler internalEventHandler;
	private InternalComponentListener internalComponentListener;
	private UtilDateModel model = new UtilDateModel();

	public DatePickerCSC() throws Exception {
		model.setValue(FechaManagerUtil.getDateTimeFromPC());
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Hoy");
		p.put("text.month", "Mes");
		p.put("text.year", "Año");

		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		this.datePanel = datePanel;

		// Initialise Variables
		popup = null;
		datePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		internalEventHandler = new InternalEventHandler();
		internalComponentListener = new InternalComponentListener();		

		// Create Layout
		setLayout(new BorderLayout(0, 0));

		formattedTextField = new JFormattedTextField(new DateComponentFormatterCSC());

		setTextFieldValue(formattedTextField, model.getYear(), model.getMonth(), model.getDay(), model.isSelected());
		add(formattedTextField);
		add(formattedTextField, BorderLayout.CENTER);

		// Add and Configure Button
		button = new JButton("...");
		button.setFocusable(true);
		button.setFont(Common.getStandarFont());
		
		add(button);
		add(formattedTextField, BorderLayout.WEST);

		// Do layout formatting
		doResize();

		// Add event listeners
		addHierarchyBoundsListener(internalEventHandler);
		button.addActionListener(internalEventHandler);
		button.addFocusListener(internalComponentListener);
		formattedTextField.addPropertyChangeListener("value", internalEventHandler);
		formattedTextField.addFocusListener(internalComponentListener);
		datePanel.addActionListener(internalEventHandler);
		datePanel.getModel().addChangeListener(internalEventHandler);
	}

	public void addFocusListener(FocusAdapter focusAdapter) {
		formattedTextField.addFocusListener(focusAdapter);
	}
	
	private void doResize() {
		FontMetrics fontMetrics = getFontMetrics(button.getFont());
		int anchoTexto = fontMetrics.stringWidth(button.getText());
		
		int wPanel = (int) this.getPreferredSize().getWidth();
		int hPanel = (int) this.getPreferredSize().getHeight();
		int wBoton = anchoTexto + 10;
		//button.setPreferredSize(new Dimension(hPanel, hPanel));
		button.setPreferredSize(new Dimension(wBoton, hPanel));
		button.setMaximumSize(new Dimension(wBoton, hPanel));
		button.setMinimumSize(new Dimension(wBoton, hPanel));
		
		//formattedTextField.setPreferredSize(new Dimension(wPanel - hPanel - 1, hPanel));
		formattedTextField.setPreferredSize(new Dimension(wPanel - wBoton, hPanel));
	}
	
	@Override
	public void setFont(Font font) {
		super.setFont(font);
		if((button != null) && (formattedTextField != null)){
			button.setFont(font);
			formattedTextField.setFont(font);
			doResize();
		}
	}

	public void addActionListener(ActionListener actionListener) {
		datePanel.addActionListener(actionListener);
	}

	public void removeActionListener(ActionListener actionListener) {
		datePanel.removeActionListener(actionListener);
	}

	public DateModel<?> getModel() {
		return datePanel.getModel();
	}

	public void setTextEditable(boolean editable) {
		formattedTextField.setEditable(editable);
	}

	public boolean isTextEditable() {
		return formattedTextField.isEditable();
	}

	public void setButtonFocusable(boolean focusable) {
		button.setFocusable(focusable);
	}

	public boolean getButtonFocusable() {
		return button.isFocusable();
	}

	public JDatePanel getJDateInstantPanel() {
		return datePanel;
	}

	public JFormattedTextField getJFormattedTextField() {
		return formattedTextField;
	}

	private void showPopup() {
		if (popup == null) {
			Window win = SwingUtilities.getWindowAncestor(this);
			Rectangle re = win.getGraphicsConfiguration().getBounds();

			PopupFactory fac = new PopupFactory();
			//No se porque, pero si no hago esto datePanel.getHeight() es igual a 0;
			popup = fac.getPopup(this, datePanel, 0, 0);
			popup.show();
			popup.hide();
			
			Point xy = getLocationOnScreen();
			datePanel.setVisible(true);
			int posY = (int) xy.getY() + this.getHeight();
			if (posY + datePanel.getHeight() > re.getHeight()) {
				posY = (int) xy.getY() - datePanel.getHeight();
			}
			popup = fac.getPopup(this, datePanel, (int) xy.getX(), posY);
			popup.show();
		}
	}

	private void hidePopup() {
		if (popup != null) {
			popup.hide();
			popup = null;
		}
	}

	private class InternalComponentListener implements FocusListener {
		public void focusGained(FocusEvent arg0) {
		}

		public void focusLost(FocusEvent arg0) {
			hidePopup();
		}
	}

	private class InternalEventHandler implements ActionListener, HierarchyBoundsListener, ChangeListener, PropertyChangeListener {

		public void ancestorMoved(HierarchyEvent arg0) {
			hidePopup();
		}

		public void ancestorResized(HierarchyEvent arg0) {
			hidePopup();
		}

		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == button) {
				if (popup == null) {
					showPopup();
				} else {
					hidePopup();
				}
			} else if (arg0.getSource() == datePanel) {
				hidePopup();
			}
		}

		public void stateChanged(ChangeEvent arg0) {
			if (arg0.getSource() == datePanel.getModel()) {
				DateModel<?> model = datePanel.getModel();
				setTextFieldValue(formattedTextField, model.getYear(), model.getMonth(), model.getDay(), model.isSelected());
			}
		}

		public void propertyChange(PropertyChangeEvent evt) {
			if (formattedTextField.isEditable() && formattedTextField.getValue() != null) {
				Calendar value = (Calendar) formattedTextField.getValue();
				datePanel.getModel().setDate(value.get(Calendar.YEAR), value.get(Calendar.MONTH), value.get(Calendar.DATE));
				datePanel.getModel().setSelected(true);
			}
		}
	}

	public boolean isDoubleClickAction() {
		return datePanel.isDoubleClickAction();
	}

	public boolean isShowYearButtons() {
		return datePanel.isShowYearButtons();
	}

	public void setDoubleClickAction(boolean doubleClickAction) {
		datePanel.setDoubleClickAction(doubleClickAction);
	}

	public void setShowYearButtons(boolean showYearButtons) {
		datePanel.setShowYearButtons(showYearButtons);
	}

	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		doResize();
	}

	private void setTextFieldValue(JFormattedTextField textField, int year, int month, int day, boolean isSelected) {
		if (!isSelected) {
			textField.setValue(null);
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, day, 0, 0, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			textField.setValue(calendar);
		}
	}

	class DateComponentFormatterCSC extends DateComponentFormatter {

		private static final long serialVersionUID = 1L;

		@Override
		public Object stringToValue(String text) throws ParseException {
			text = FechaManagerUtil.formatearFecha(text);

			return super.stringToValue(text);
		}
	}

	public Date getDate() {
		if (this.getJFormattedTextField().getText().trim().equals("")) {
			return null;
		} else {
			return FechaManagerUtil.String2Date(this.getJFormattedTextField().getText());
		}
	}

	public void setFecha(Date fecha) {
		model.setValue(fecha);
	}

	public void resetear() {
		setFecha(FechaManagerUtil.getDateTimeFromPC());
	}
}
