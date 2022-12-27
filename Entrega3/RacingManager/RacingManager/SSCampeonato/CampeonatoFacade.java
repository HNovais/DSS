package RacingManager.SSCampeonato;

import RacingManager.Piloto;
import RacingManager.SSCarro.Carro;
import RacingManager.SSCorrida.Corrida;
import data.CampeonatoDAO;

import java.util.List;

public class CampeonatoFacade implements ICampeonato {

	CampeonatoDAO campeonatos;
	private List<String> nomeJogadores;
	private int numeroJogadores;

	/**
	 * 
	 * @param n
	 */
	public void numeroJogadores(int n) {
		// TODO - implement CampeonatoFacade.numeroJogadores
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param l
	 */
	public void nomeJogadores(List<String> l) {
		// TODO - implement CampeonatoFacade.nomeJogadores
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idJ
	 * @param p
	 * @param idCamp
	 */
	public void escolhePiloto(String idJ, Piloto p, String idCamp) {
		// TODO - implement CampeonatoFacade.escolhePiloto
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idJ
	 * @param idCamp
	 * @param ca
	 */
	public void escolheCarro(String idJ, String idCamp, Carro ca) {
		// TODO - implement CampeonatoFacade.escolheCarro
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param idCamp
	 * @return
	 */
	public Corrida indicaCorrida(String idCamp) {
		// TODO - implement CampeonatoFacade.indicaCorrida
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idCamp
	 * @param idJ
	 */
	public int indicaAfinacoes(String idCamp, String idJ) {
		// TODO - implement CampeonatoFacade.indicaAfinacoes
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idCamp
	 * @param idJ
	 */
	public Boolean verificaAfinacoes(String idCamp, String idJ) {
		// TODO - implement CampeonatoFacade.verificaAfinacoes
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param newCamp
	 */
	public void novoCampeonato(Campeonato newCamp) {
		// TODO - implement CampeonatoFacade.novoCampeonato
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idCamp
	 * @param idJ
	 */
	public void afinaCarro(String idCamp, String idJ) {
		// TODO - implement CampeonatoFacade.afinaCarro
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idCamp
	 * @param idJ
	 * @param val
	 */
	public void alteraDownforce(String idCamp, String idJ, float val) {
		// TODO - implement CampeonatoFacade.alteraDownforce
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idJ
	 * @param idCamp
	 * @param m
	 */
	public void escolheMotor(String idJ, String idCamp, String m) {
		// TODO - implement CampeonatoFacade.escolheMotor
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idJ
	 * @param idCamp
	 * @param p
	 */
	public void escolhePneus(String idJ, String idCamp, String p) {
		// TODO - implement CampeonatoFacade.escolhePneus
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idCamp
	 */
	public int indicaMeteorologia(String idCamp) {
		// TODO - implement CampeonatoFacade.indicaMeteorologia
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idCamp
	 */
	public String simulaCorrida(String idCamp) {
		// TODO - implement CampeonatoFacade.simulaCorrida
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param newCamp
	 * @param numeroJogadores
	 */
	public void adicionaCampeonato(Campeonato newCamp, int numeroJogadores) {
		// TODO - implement CampeonatoFacade.adicionaCampeonato
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param fin
	 */
	public void updateClassificacao(String fin) {
		// TODO - implement CampeonatoFacade.updateClassificacao
		throw new UnsupportedOperationException();
	}

}