package RacingManager;

import java.util.List;

public class Circuito {

	private String nomeCircuito;
	private int distancia;
	private List<Elemento> elementos;

	public List<Elemento> getElementos() {
		return elementos;
	}

	public String getNomeCircuito() {
		return nomeCircuito;
	}

	public void setNomeCircuito(String nomeCircuito) {
		this.nomeCircuito = nomeCircuito;
	}

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}

	public void setElementos(List<Elemento> elementos) {
		this.elementos = elementos;
	}
}