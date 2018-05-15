package ar.com.rp.ui.common;

public class GeneralSettings {
	
	private Integer sizeStandar = 15;
	private Integer sizeMenu = 15;
	private Integer sizeBotonBarra = 10;
	private String nombreFont = "Tahoma";
	private String separadorDecimal = ",";
	private String separadorMiles = ".";
	
	public Integer getSizeStandar() {
		return sizeStandar;
	}
	public void setSizeStandar(Integer sizeStandar) {
		this.sizeStandar = sizeStandar;
	}
	public Integer getSizeMenu() {
		return sizeMenu;
	}
	public void setSizeMenu(Integer sizeMenu) {
		this.sizeMenu = sizeMenu;
	}
	public String getNombreFont() {
		return nombreFont;
	}
	public void setNombreFont(String nombreFont) {
		this.nombreFont = nombreFont;
	}
	public String getSeparadorDecimal() {
		return separadorDecimal;
	}
	public void setSeparadorDecimal(String separadorDecimal) {
		this.separadorDecimal = separadorDecimal;
	}
	public String getSeparadorMiles() {
		return separadorMiles;
	}
	public void setSeparadorMiles(String separadorMiles) {
		this.separadorMiles = separadorMiles;
	}
	public Integer getSizeBotonBarra() {
		return sizeBotonBarra;
	}
	public void setSizeBotonBarra(Integer sizeBotonBarra) {
		this.sizeBotonBarra = sizeBotonBarra;
	}
	
}
