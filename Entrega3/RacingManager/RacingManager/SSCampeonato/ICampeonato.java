package RacingManager.SSCampeonato;

import RacingManager.Piloto;
import RacingManager.SSCarro.Carro;
import RacingManager.SSCorrida.Corrida;
import data.CampeonatoDAO;
import data.UtilizadorDAO;

import java.util.List;
import java.util.Map;

public interface ICampeonato {

	public void saveResults(Map<String, String> usernames, UtilizadorDAO utilizadorDAO);
	public CampeonatoDAO getCampeonatos();
	public void setCampeonatos(CampeonatoDAO campeonatos);
	public Campeonato getCampeonatoAtual();
	public void setCampeonatoAtual(Campeonato campeonatoAtual);
	/**
	 * 
	 * @param n
	 */
	void numeroJogadores(int n);

	/**
	 * 
	 * @param l
	 */
	void nomeJogadores(List<String> l);

	/**
	 * 
	 * @param idJ
	 * @param p
	 * @param idCamp
	 */
	void escolhePiloto(String idJ, Piloto p, String idCamp);

	/**
	 * 
	 * @param idJ
	 * @param idCamp
	 * @param ca
	 */
	void escolheCarro(String idJ, String idCamp, Carro ca);

	/**
	 *
	 * @param idCamp
	 * @return
	 */
	Corrida indicaCorrida(String idCamp);

	/**
	 * 
	 * @param idCamp
	 * @param idJ
	 */
	String indicaAfinacoes(String idCamp, String idJ);

	/**
	 * 
	 * @param idCamp
	 * @param idJ
	 */
	Boolean verificaAfinacoes(String idCamp, String idJ);

	/**
	 * 
	 * @param idCamp
	 */
	void novoCampeonato(String idCamp);

	/**
	 * 
	 * @param idCamp
	 * @param idJ
	 */
	void afinaCarro(String idCamp, String idJ);

	/**
	 * 
	 * @param idCamp
	 * @param idJ
	 * @param val
	 */
	void alteraDownforce(String idCamp, String idJ, float val);

	/**
	 * 
	 * @param idJ
	 * @param idCamp
	 * @param m
	 */
	void escolheMotor(String idJ, String idCamp, String m);

	/**
	 * 
	 * @param idJ
	 * @param idCamp
	 * @param m
	 */
	void escolhePneus(String idJ, String idCamp, String m);

	/**
	 * 
	 * @param idCamp
	 */

	int indicaMeteorologia(String idCamp);

	/**
	 * 
	 * @param idCamp
	 */
	String simulaCorrida(String idCamp);

	/**
	 * 
	 * @param newCamp
	 * @param numeroJogadores
	 */
	void adicionaCampeonato(Campeonato newCamp, int numeroJogadores);

	/**
	 * 
	 * @param fin
	 */
	void updateClassificacao(String fin);

}