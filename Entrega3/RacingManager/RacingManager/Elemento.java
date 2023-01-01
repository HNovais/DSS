package RacingManager;

public class Elemento {

	private String categoria;
	private String GDU;

	public Elemento(String categoria, String gdu){
		this.categoria = categoria;
		this.GDU = gdu;
	}

	public Elemento(){
		this.categoria = null;
		this.GDU = null;
	}

	public String getGDU() {
		return GDU;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public void setGDU(String GDU) {
		this.GDU = GDU;
	}
}