package ar.com.rp.ui.componentes;

public class ItemComboBox {
	private int id;
	private String description;

	public ItemComboBox(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public String toString() {
		return description;
	}
}
