package RacingManager;

public class Piloto {

	private String nomePiloto;
	private Float SVA;
	private Float CTS;

	// Getters and Setters
	public String getNomePiloto() {
		return nomePiloto;
	}

	public void setNomePiloto(String nomePiloto) {
		this.nomePiloto = nomePiloto;
	}

	public Float getSVA() {
		return SVA;
	}

	public void setSVA(Float SVA) {
		this.SVA = SVA;
	}

	public Float getCTS() {
		return CTS;
	}

	public void setCTS(Float CTS) {
		this.CTS = CTS;
	}

	@Override
	public String toString() {
		return "Piloto{" +
				"nomePiloto='" + nomePiloto + '\'' +
				", SVA=" + SVA +
				", CTS=" + CTS +
				'}';
	}
}