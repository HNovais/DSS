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

	CircuitoDAO circuitos;
	UtilizadorDAO utilizadores;
	PilotoDAO pilotos;
	CampeonatoFacade campF = new CampeonatoFacade();
	CarroFacade carroF = new CarroFacade();

	/**
	 * 
	 * @param idJ
	 * @param idCamp
	 * @param p
	 */
	public void escolhePneus(String idJ, String idCamp, String p) {
		campF.escolhePneus(idJ, idCamp, p);
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
		campF.alteraDownforce(idCamp, idJ, val);
	}

	/**
	 * 
	 * @param idJ
	 * @param idCarro
	 * @param idCamp
	 */
	public void escolheCarro(String idJ, String idCarro, String idCamp) {
		Carro ca = carroF.getCarro(idCarro);
		campF.escolheCarro(idJ, idCamp, ca);
	}

	/**
	 * 
	 * @param idJ
	 * @param idCamp
	 * @param m
	 */
	public void escolheMotor(String idJ, String idCamp, String m) {
		campF.escolheMotor(idJ, idCamp, m);
	}

	/**
	 * 
	 * @param idJ
	 * @param idP
	 * @param idCamp
	 */
	public void escolhePiloto(String idJ, String idP, String idCamp) {
		Piloto p = pilotos.get(idP);
		campF.escolhePiloto(idJ, p, idCamp);
	}

	/**
	 * 
	 * @param idCamp
	 * @param idJ
	 */
	public String indicaAfinacoes(String idCamp, String idJ) {
		return campF.indicaAfinacoes(idCamp, idJ);
	}

	/**
	 * 
	 * @param idCamp
	 */
	public Corrida indicaCorrida(String idCamp) {
		return campF.indicaCorrida(idCamp);
	}

	/**
	 * 
	 * @param idCamp
	 */
	public int indicaMeteorologia(String idCamp) {
		return this.campF.indicaMeteorologia(idCamp);
	}

	/**
	 * 
	 * @param list
	 */
	public void nomeJogadores(List<String> list) {
		campF.nomeJogadores(list);
	}

	/**
	 * 
	 * @param idCamp
	 */
	public void novoCampeonato(String idCamp) {
		campF.novoCampeonato(idCamp);
	}

	/**
	 * 
	 * @param n
	 */
	public void numeroJogadores(int n) {
		campF.numeroJogadores(n);
	}

	/**
	 * 
	 * @param idCamp
	 */
	public String simularCorrida(String idCamp) {
		return campF.simulaCorrida(idCamp);
	}

	/**
	 * 
	 * @param idCamp
	 * @param idJ
	 */
	public boolean verificaAfinacoes(String idCamp, String idJ) {
		return campF.verificaAfinacoes(idCamp, idJ);
	}

}