package ar.com.rp.ui.componentes;

import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import ar.com.rp.ui.common.Common;

public class JComboBoxRP extends JComboBox<ItemComboBox> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Integer ID_SIN_SELECCIONAR = -1;

	public JComboBoxRP() {
		super();
		implementacionGenerica();
	}

	private void implementacionGenerica() {
		agregarItemSinSeleccionar();
		setEditable(false);
		setFont(Common.getStandarFont());
	}

	public JComboBoxRP(ComboBoxModel<ItemComboBox> aModel) {
		super(aModel);
		implementacionGenerica();		
	}
	
	public JComboBoxRP(ItemComboBox[] items) {
		super(items);
		implementacionGenerica();	
	}
	
	public JComboBoxRP(Vector<ItemComboBox> items){
		super(items);
		implementacionGenerica();	
	}
	
	public void setListaItem(List<ItemComboBox> listaItemComboBox) {

		for (ItemComboBox item : listaItemComboBox) {
			addItem(item);
		}
	}

	public Boolean isSinSeleccionar() {
		return getIDSelected() == ID_SIN_SELECCIONAR;
	}
	
	public void setItemSelccionado(Integer id) {
		for(int i = 0; i < getModel().getSize(); i++) {
			ItemComboBox item  = (ItemComboBox) getModel().getElementAt(i);
			if(item.getId() == id) {
				setSelectedIndex(i);
			}
		}
	}
	
	public Integer getIDSelected() {
		 Object obj = getSelectedItem();
		 if(obj != null) {
			 ItemComboBox item = (ItemComboBox) obj;
			 return item.getId();
		 } 
		 return ID_SIN_SELECCIONAR;
	}

	public void setSinSeleccionar() {
		setSelectedIndex(0);
	}

	public void vaciarLista() {
		removeAllItems();
		agregarItemSinSeleccionar();
		setSinSeleccionar();
	}

	private void agregarItemSinSeleccionar() {
		addItem(new ItemComboBox(ID_SIN_SELECCIONAR, "Sin Seleccionar"));
	}
}