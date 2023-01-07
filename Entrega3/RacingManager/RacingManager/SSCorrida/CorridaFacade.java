package RacingManager.SSCorrida;

import RacingManager.SSCarro.*;

import java.util.List;
import java.util.Map;

public class CorridaFacade implements ICorrida {

	private Map<String, Corrida> corridas;

	public String generateMeteorologia() {
		// Generate a random number between 0 and 1
		double randomDouble = Math.random();

		// If the random number is less than 0.5, return 0. Otherwise, return 1.
		if (randomDouble < 0.5) {
			return "Chuva"; // Quando é 0
		} else {
			return "Seco"; // Quando é 1
		}
	}

	public int indicaMeteorologia() {
		//String m = setMeteorologia(generateMeteorologia());
		throw new UnsupportedOperationException();
	}


	/**
	 * 
	 * @param modo
	 */
	public String simulaCorrida(String modo) {
		// TODO - implement CorridaFacade.simulaCorrida
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateDNF(List<Carro> u) {
	}

	@Override
	public void updatePosicao(List<Carro> u) {
	}

/*	public void updateUltrapassar(Posicao p) {
		// TODO - implement CorridaFacade.updateUltrapassar
		throw new UnsupportedOperationException();
	}*/

	public void updateTempo() {
		// TODO - implement CorridaFacade.updateTempo
		throw new UnsupportedOperationException();
	}

	public void printResumo() {
		// TODO - implement CorridaFacade.printResumo
		throw new UnsupportedOperationException();
	}

}