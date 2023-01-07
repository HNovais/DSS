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
		this.numeroJogadores = n;
	}

	/**
	 * 
	 * @param l
	 */
	public void nomeJogadores(List<String> l) {
		this.nomeJogadores = l;
	}

	/**
	 * 
	 * @param idJ
	 * @param p
	 * @param idCamp
	 */
	public void escolhePiloto(String idJ, Piloto p, String idCamp) {
		Campeonato c = campeonatos.get(idCamp);
		c.escolhePiloto(idJ, p);
	}

	/**
	 * 
	 * @param idJ
	 * @param idCamp
	 * @param ca
	 */
	public void escolheCarro(String idJ, String idCamp, Carro ca) {
		Campeonato c = campeonatos.get(idCamp);
		c.escolheCarro(idJ, ca);
	}

	/**
	 *
	 * @param idCamp
	 * @return
	 */
	public Corrida indicaCorrida(String idCamp) {
		Campeonato c = campeonatos.get(idCamp);
		return c.indicaCorrida();
	}

	/**
	 * 
	 * @param idCamp
	 * @param idJ
	 */
	public String indicaAfinacoes(String idCamp, String idJ) {
		Campeonato c = this.campeonatos.get(idCamp);
		return c.indicaAfinacoes(idJ);
	}

	/**
	 * 
	 * @param idCamp
	 * @param idJ
	 */
	public Boolean verificaAfinacoes(String idCamp, String idJ) {
		Campeonato c = campeonatos.get(idCamp);
		return c.verificaAfinacoes(idJ);
	}


	/**
	 * 
	 * @param idCamp
	 */
	public void novoCampeonato(String idCamp) {
		Campeonato newCamp = campeonatos.get(idCamp);
		//adicionaCampeonato(newCamp, numeroJogadores);
		newCamp.novoCampeonato(nomeJogadores);
	}

	/**
	 * 
	 * @param idCamp
	 * @param idJ
	 */
	public void afinaCarro(String idCamp, String idJ) {
		Campeonato c = campeonatos.get(idCamp);
		c.afinaCarro(idJ);
	}

	/**
	 * 
	 * @param idCamp
	 * @param idJ
	 * @param val
	 */
	public void alteraDownforce(String idCamp, String idJ, float val) {
		Campeonato c = campeonatos.get(idCamp);
		c.alteraDownforce(idJ, val);
	}

	/**
	 * 
	 * @param idJ
	 * @param idCamp
	 * @param m
	 */
	public void escolheMotor(String idJ, String idCamp, String m) {
		Campeonato c = campeonatos.get(idCamp);
		c.escolheMotor(idJ, m);
	}

	/**
	 * 
	 * @param idJ
	 * @param idCamp
	 * @param p
	 */
	public void escolhePneus(String idJ, String idCamp, String p) {
		Campeonato c = campeonatos.get(idCamp);
		c.escolhePneus(idJ, p);
	}

	/**
	 * 
	 * @param idCamp
	 */
	public int indicaMeteorologia(String idCamp) {
		Campeonato c = this.campeonatos.get(idCamp);
		return c.indicaMeteorologia();
	}

	/**
	 * 
	 * @param idCamp
	 */
	public String simulaCorrida(String idCamp) {
		Campeonato c = campeonatos.get(idCamp);
		return c.simulaCorrida();
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