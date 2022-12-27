package RacingManager;

import RacingManager.SSCorrida.*;
import RacingManager.SSCampeonato.*;
import RacingManager.SSCarro.*;
import data.CircuitoDAO;
import data.PilotoDAO;
import data.UtilizadorDAO;

import java.util.List;
import java.util.Map;

public class RacingManagerFacade implements IRacingManager {

//	CircuitoDAO circuitos;
//	UtilizadorDAO utilizadores;
//	PilotoDAO pilotos;

	private Map<String, Circuito> circuitos;
	private Map<String, Utilizador> utilizadores;
	private Map<String, Piloto> pilotos;

	/**
	 * 
	 * @param idJ
	 * @param idCamp
	 * @param p
	 */
	public void escolhePneus(String idJ, String idCamp, String p) {
		escolhePneus(idJ, idCamp, p);
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idCamp
	 * @param idJ
	 */
	public void afinaCarro(String idCamp, String idJ) {
		// TODO - implement RacingManagerFacade.afinaCarro
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idCamp
	 * @param idJ
	 * @param val
	 */
	public void alteraDownforce(String idCamp, String idJ, float val) {
		// TODO - implement RacingManagerFacade.alteraDownforce
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idJ
	 * @param idCarro
	 * @param idCamp
	 */
	public void escolheCarro(String idJ, String idCarro, String idCamp) {
		// TODO - implement RacingManagerFacade.escolheCarro
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idJ
	 * @param idCamp
	 * @param m
	 */
	public void escolheMotor(String idJ, String idCamp, String m) {
		// TODO - implement RacingManagerFacade.escolheMotor
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idJ
	 * @param idP
	 * @param idCamp
	 */
	public void escolhePiloto(String idJ, String idP, String idCamp) {
		// TODO - implement RacingManagerFacade.escolhePiloto
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idCamp
	 * @param idJ
	 */
	public String indicaAfinacoes(String idCamp, String idJ) {
		// TODO - implement RacingManagerFacade.indicaAfinacoes
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idCamp
	 */
	public Corrida indicaCorrida(String idCamp) {
		// TODO - implement RacingManagerFacade.indicaCorrida
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idCamp
	 */
	public int indicaMeteorologia(String idCamp) {
		// TODO - implement RacingManagerFacade.indicaMeteorologia
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param list
	 */
	public void nomeJogadores(List<String> list) {
		// TODO - implement RacingManagerFacade.nomeJogadores
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idCamp
	 */
	public void novoCampeonato(String idCamp) {
		// TODO - implement RacingManagerFacade.novoCampeonato
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param n
	 */
	public void numeroJogadores(int n) {
		// TODO - implement RacingManagerFacade.numeroJogadores
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idCamp
	 */
	public String simularCorrida(int idCamp) {
		// TODO - implement RacingManagerFacade.simularCorrida
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idCamp
	 * @param idJ
	 */
	public boolean verificaAfinacoes(String idCamp, String idJ) {
		// TODO - implement RacingManagerFacade.verificaAfinacoes
		throw new UnsupportedOperationException();
	}

}