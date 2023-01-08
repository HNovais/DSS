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

	private int voltas;
	private List<Corrida> corridas = new ArrayList<>();
	public List<Circuito> circuitos = new ArrayList<>();
	public Set<Jogador> jogadores = new HashSet<>();
	public Map<String, List<Integer>> classificacao = new HashMap<>();
	public List<String> classificacaoFinal = new ArrayList<>();
	private int contador = 0;
	public Map<String, List<Integer>> pontuacao = new HashMap<>();
	private Map<String, List<String>> pontCategoria = new HashMap<>();
	private CorridaFacade corrF;
	// CorridaDAO corrDAO;


	public List<String> getClassificacaoFinal() {
		return classificacaoFinal;
	}

	public void setClassificacaoFinal(List<String> classificacaoFinal) {
		this.classificacaoFinal = classificacaoFinal;
	}

	public int getVoltas() {
		return voltas;
	}

	public void setVoltas(int voltas) {
		this.voltas = voltas;
	}

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

		Corrida corrida = new Corrida(circuito, getVoltas());
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

	public String getCategoriaJog(String idJ){
		Jogador j = jogadores.stream()
				.filter(jog -> jog.getNomeJogador().equalsIgnoreCase(idJ))
				.findFirst()
				.orElse(null);
		return j.getCarro().getCategoria();
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

	public void printFinal() {
		Map<String, List<String>> pontuacaoCategoria = new HashMap<>();
		// Ordena a lista de pontuações por pontuação
		List<Map.Entry<String, Integer>> list = new ArrayList<>(this.getPontuacao().entrySet());
		Collections.sort(list, (a, b) -> b.getValue() - a.getValue());

		int x = 1;
		for (Map.Entry<String, Integer> entry : list) {
			String key = entry.getKey();
			int value = entry.getValue();
			System.out.println(x + "º: " + key + " ---> " + value);
			this.classificacaoFinal.add(key);
			x++;
		}
		System.out.println();

		for (Map.Entry<String, Integer> entry : list) {
			String nome = entry.getKey();
			//int pontuacao = entry.getValue();
			String categoria = this.getCategoriaJog(nome);
			if(!pontuacaoCategoria.containsKey(categoria)) {
				List<String> listAux = new ArrayList<>();
				listAux.add(nome);
				pontuacaoCategoria.put(categoria, listAux);
			}
			else {
				pontuacaoCategoria.get(categoria).add(nome);
				//pontuacaoCategoria.put(categoria, listAux);
			}
		}
		int y;
		for (Map.Entry<String, List<String>> entry : pontuacaoCategoria.entrySet()) {
			String categoria = entry.getKey();
			List<String> jogadores = entry.getValue();
			System.out.println("Categoria: " + categoria);
			y = 1;
			for (String jogador : jogadores) {
				System.out.println(y + "º: " + jogador);
				y++;
			}
		}

	}
/*
	public void printFinal() {
		System.out.println("------Classificação Geral------");
		Map<String, Integer> c = getPontuacao();

		List<Map.Entry<String, Integer>> entries = new ArrayList<>(c.entrySet());
		Collections.sort(entries, (a, b) -> b.getValue() - a.getValue());

		int x = 1;

		for (Map.Entry<String, Integer> entry : entries) {
			String key = entry.getKey();
			int value = entry.getValue();
			System.out.println(x + "º: " + key + " ---> " + value);
			this.classificacaoFinal.add(key);
			x++;
		}

		this.getPontuacaoCategoria(this.getPontuacao());

	}
*/
	/*
		private String resumoCorrida(){
		StringBuilder sb = new StringBuilder();
		List<Jogador> listAux = new ArrayList<>();

		for(int i = 1; i <= posicao.size(); i++){
			sb.append(i + "º ---> " + posicao.get(i-1).getNomeJogador());
			sb.append("\n");
		}

		posicaoCategoria();

		for (String categoria : posCategoria.keySet()) {
			sb.append(categoria);
			sb.append(": ");
			listAux = posCategoria.get(categoria);
			for (int i = 0; i < listAux.size(); i++) {
				if (i+1 == posCategoria.get(categoria).size()) {
					sb.append(".");
				} else {
					sb.append(listAux.get(i).getNomeJogador());
					sb.append(", ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	 */

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

	public void generateCorridas(){
		for(int i = 0; i < circuitos.size(); i++){
			Corrida nextCorrida = nextCorrida(i);
			Circuito c = getCircuitos().get(i);

			nextCorrida.setCircuito(c);
			List<Jogador> jogadores = new ArrayList<>(getJogadores());
			nextCorrida.setPosicao(jogadores);

			getCorridas().add(nextCorrida);
		}
	}
}