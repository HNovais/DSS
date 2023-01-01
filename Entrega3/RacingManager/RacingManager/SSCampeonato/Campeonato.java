package RacingManager.SSCampeonato;

import RacingManager.SSCarro.Carro;
import RacingManager.SSCorrida.*;
import RacingManager.*;
import data.CorridaDAO;

import java.util.*;

public class Campeonato {

	private int id;
	private String nome;
	private int participantes;
	private List<Corrida> corridas = new ArrayList<>();
	public List<Circuito> circuitos = new ArrayList<>();
	public Set<Jogador> jogadores = new HashSet<>();
	private Map<String, List<Integer>> classificacao;
	private int contador = 0;
	private Map<String, List<Integer>> pontuacao;
	// private Map<String, List<String>> pontCategoria;
	private CorridaFacade corrF;
	// CorridaDAO corrDAO;

	public void setCircuitos(List<Circuito> circuitos){ this.circuitos = circuitos; }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getParticipantes() {
		return participantes;
	}

	public void setParticipantes(int participantes) {
		this.participantes = participantes;
	}

	public void setContador(int contador) {
		this.contador = contador;
	}

	public List<Corrida> getCorridas() {
		return corridas;
	}

	public void setCorridas(List<Corrida> corridas) {
		this.corridas = corridas;
	}

	public Set<Jogador> getJogadores() {
		return jogadores;
	}

	public void setJogadores(Set<Jogador> jogadores) {
		this.jogadores = jogadores;
	}

	public Corrida nextCorrida(int i){
		Circuito circuito = circuitos.get(i);

		Corrida corrida = new Corrida(circuito);
		corrida.setMeteorologia();

		return corrida;
	}

	/**
	 * 
	 * @param idJ
	 * @param ca
	 */
	public void escolheCarro(String idJ, Carro ca) {

		Jogador j = jogadores.stream()
					.filter(jog -> jog.getId() == idJ)
					.findFirst()
					.orElse(null);
		j.escolheCarro(ca);
	}

	/**
	 * 
	 * @param idJ
	 * @param m
	 */
	public void escolheMotor(String idJ, String m) {
		Jogador j = jogadores.stream()
					.filter(jog -> jog.getId() == idJ)
					.findFirst()
					.orElse(null);
		j.escolheMotor(m);
	}

	/**
	 * 
	 * @param idJ
	 * @param p
	 */
	public void escolhePneus(String idJ, String p) {
		Jogador j = jogadores.stream()
					.filter(jog -> jog.getId() == idJ)
					.findFirst()
					.orElse(null);
		j.escolhePneus(p);
	}

	/**
	 * 
	 * @param idJ
	 */
	public String indicaAfinacoes(String idJ) {

		int a = totalAfinacoes();
		Jogador j = jogadores.stream()
					.filter(jog -> jog.getId() == idJ)
					.findFirst()
					.orElse(null);

		return j.indicaAfinacoes(a);
	}

	public Corrida indicaCorrida() {
		int cont = getContador();
		// return corrDAO.get(corridas.get(cont));
		return corridas.get(cont);
	}

	public int indicaMeteorologia() {
		Corrida corr = this.indicaCorrida();
		return 0;
	}

	/**
	 * 
	 * @param listaJogadores
	 */
	public void novoCampeonato(List<String> listaJogadores) {
		for(String jog : listaJogadores){
			Jogador jogador = new Jogador(jog);
			jogadores.add(jogador);
		}
	}

	/**
	 * 
	 * @param idJ
	 */
	public boolean verificaAfinacoes(String idJ) {
		int a = totalAfinacoes();
		Jogador j = jogadores.stream()
					.filter(jog -> jog.getId() == idJ)
					.findFirst()
					.orElse(null);
		return j.verificaAfinacoes(a);
	}

	public int totalAfinacoes() {
		return (int) (corridas.size() * 0.666666);
	}

	/**
	 * 
	 * @param idJ
	 * @param p
	 */
	public void escolhePiloto(String idJ, Piloto p) {
		Jogador j = jogadores.stream()
					.filter(jog -> jog.getId() == idJ)
					.findFirst()
					.orElse(null);

		j.escolhePiloto(p);
	}

	public void alteraDownforce(String idJ, float val){
		Jogador j = jogadores.stream()
					.filter(jog -> jog.getId() == idJ)
					.findFirst()
					.orElse(null);
		j.alteraDownforce(val);
	}

	public String simulaCorrida(){
		Corrida corr = indicaCorrida();
		// modo do jogo --> Normal ou Premium
		String updates = corr.simulaCorrida();
		//Faz Updates
		return updates;
	}
	public int getContador() {
		return contador;
	}

	public void afinaCarro(String idJ) {
		Jogador j = jogadores.stream()
				.filter(jog -> jog.getId() == idJ)
				.findFirst()
				.orElse(null);
		j.afinaCarro();

	}
}