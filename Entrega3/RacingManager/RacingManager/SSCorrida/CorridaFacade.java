package RacingManager.SSCorrida;

import RacingManager.SSCarro.*;
import data.CorridaDAO;

import java.util.List;
import java.util.Map;

public class CorridaFacade implements ICorrida {

	private Map<String, Corrida> corridas;

	public int indicaMeteorologia() {
		// TODO - implement CorridaFacade.indicaMeteorologia
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