package RacingManager.SSCorrida;

import RacingManager.SSCarro.Carro;

import java.util.List;

public interface ICorrida {

	int indicaMeteorologia();

	/**
	 * 
	 * @param modo
	 */
	String simulaCorrida(String modo);

	/**
	 * 
	 * @param p
	 */
	//void updateUltrapassar(Posicao p);

	/**
	 * 
	 * @param u
	 */
	void updateDNF(List<Carro> u);

	/**
	 * 
	 * @param u
	 */
	void updatePosicao(List<Carro> u);

	void updateTempo();

	void printResumo();

}