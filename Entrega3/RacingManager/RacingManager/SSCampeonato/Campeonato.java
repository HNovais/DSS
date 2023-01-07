package RacingManager.SSCampeonato;

import RacingManager.SSCarro.Carro;
import RacingManager.SSCorrida.*;
import RacingManager.*;
import com.sun.jdi.IntegerType;

import java.util.*;

public class Campeonato {

	private int id;
	private String nome;
	private int participantes;
	private List<Corrida> corridas = new ArrayList<>();
	public List<Circuito> circuitos = new ArrayList<>();
	public Set<Jogador> jogadores = new HashSet<>();
	public Map<String, List<Integer>> classificacao = new HashMap<>();
	private int contador = 0;
	public Map<String, List<Integer>> pontuacao = new HashMap<>();
	// private Map<String, List<String>> pontCategoria;
	private CorridaFacade corrF;
	// CorridaDAO corrDAO;

	public List<Circuito> getCircuitos() {
		return circuitos;
	}

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
					.filter(jog -> jog.getNomeJogador().equalsIgnoreCase(idJ))
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
					.filter(jog -> jog.getNomeJogador().equalsIgnoreCase(idJ))
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
					.filter(jog -> jog.getNomeJogador().equalsIgnoreCase(idJ))
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
					.filter(jog -> jog.getNomeJogador().equalsIgnoreCase(idJ))
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
					.filter(jog -> jog.getNomeJogador().equalsIgnoreCase(idJ))
					.findFirst()
					.orElse(null);
		return j.verificaAfinacoes(a);
	}

	public int totalAfinacoes() {
		return (int) ((circuitos.size() * 2)/3);
	}

	/**
	 * 
	 * @param idJ
	 * @param p
	 */
	public void escolhePiloto(String idJ, Piloto p) {
		Jogador j = jogadores.stream()
					.filter(jog -> jog.getNomeJogador().equalsIgnoreCase(idJ))
					.findFirst()
					.orElse(null);

		j.escolhePiloto(p);
	}

	public void alteraDownforce(String idJ, float val){
		Jogador j = jogadores.stream()
					.filter(jog -> jog.getNomeJogador().equalsIgnoreCase(idJ))
					.findFirst()
					.orElse(null);
		j.alteraDownforce(val);
	}

	public String simulaCorrida(){/*
		Corrida nextCorrida = campeonato.nextCorrida(i);
		Circuito cAtual = campeonato.getCircuitos().get(i);
		nextCorrida.setCircuito(cAtual);
		nextCorrida.addParticipantes(campeonato.getJogadores());
	*/	Corrida corr = indicaCorrida();
		// modo do jogo --> Normal ou Premium\
		String updates = corr.simulaCorrida();
		this.contador = contador + 1;

		updateClassificacao(updates);
		updatePontuacao();

		return updates;
	}

	public void printFinal(){
		System.out.println("------Classificação Geral------");
		Map<String, Integer> c = getPontuacao();

		List<Map.Entry<String, Integer>> entries = new ArrayList<>(c.entrySet());
		Collections.sort(entries, (a, b) -> b.getValue() - a.getValue());

		int x = 1;

		for (Map.Entry<String, Integer> entry : entries) {
			String key = entry.getKey();
			int value = entry.getValue();
			System.out.println("1º: " + key + " ---> " + value);
			x++;
		}
	}

	private Map<String, Integer> getPontuacao(){
		Map<String, Integer> c = new HashMap<>();

		for(String jogador : pontuacao.keySet()){
			List<Integer> valores = pontuacao.get(jogador);
			int sum = valores.stream().mapToInt(Integer::intValue).sum();
			c.put(jogador, sum);
		}

		return c;
	}
	private void updatePontuacao(){
		for(String jogador : pontuacao.keySet()){
			List<Integer> AAA = classificacao.get(jogador);
			int x = AAA.get(AAA.size() - 1);

			List<Integer> pont = pontuacao.get(jogador);
			if (pont == null)
				pont = new ArrayList<>();

			pont.add((7-x)*2);

			pontuacao.put(jogador, pont);
		}
	}
	private void updateClassificacao(String updates){
		for(int i = 1; i <= jogadores.size(); i++){
			String[] p = updates.split("\n");
			String values = p[i-1];
			String[] pp = values.split("--->");
			String jogador = pp[1].trim();

			List<Integer> list = classificacao.get(jogador);
			if (list == null)
				list = new ArrayList<>();

			list.add(i);

			classificacao.put(jogador, list);
		}
	}
	public int getContador() {
		return contador;
	}

	public void afinaCarro(String idJ) {
		Jogador j = jogadores.stream()
				.filter(jog -> jog.getNomeJogador().equalsIgnoreCase(idJ))
				.findFirst()
				.orElse(null);
		j.afinaCarro();

	}
}