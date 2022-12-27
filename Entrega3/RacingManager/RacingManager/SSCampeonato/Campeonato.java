package RacingManager.SSCampeonato;

import RacingManager.SSCarro.Carro;
import RacingManager.SSCorrida.*;
import RacingManager.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Campeonato {

	private String nome;
	private int participantes;
	private List<Corrida> corridas;
	private Set<Jogador> jogadores;
	private Map<String, List<Integer>> classificacao;
	private Map<String, List<Integer>> pontuacao;
	private Map<String, List<String>> pontCategoria;

	/**
	 * 
	 * @param idJ
	 * @param ca
	 */
	public void escolheCarro(String idJ, Carro ca) {
		// TODO - implement Campeonato.escolheCarro
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idJ
	 * @param m
	 */
	public void escolheMotor(String idJ, String m) {
		// TODO - implement Campeonato.escolheMotor
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idJ
	 * @param p
	 */
	public void escolhePneus(String idJ, String p) {
		// TODO - implement Campeonato.escolhePneus
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idJ
	 */
	public String indicaAfinacoes(String idJ) {
		// TODO - implement Campeonato.indicaAfinacoes
		throw new UnsupportedOperationException();
	}

	public Corrida indicaCorrida() {
		// TODO - implement Campeonato.indicaCorrida
		throw new UnsupportedOperationException();
	}

	public int indicaMeteorologia() {
		// TODO - implement Campeonato.indicaMeteorologia
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param listaJogadores
	 */
	public void novoCampeonato(List<String> listaJogadores) {
		// TODO - implement Campeonato.novoCampeonato
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idJ
	 */
	public boolean verificaAfinacoes(String idJ) {
		// TODO - implement Campeonato.verificaAfinacoes
		throw new UnsupportedOperationException();
	}

	public int totalAfinacoes() {
		// TODO - implement Campeonato.totalAfinacoes
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idJ
	 * @param p
	 */
	public void escolhePiloto(String idJ, Piloto p) {
		// TODO - implement Campeonato.escolhePiloto
		throw new UnsupportedOperationException();
	}

}