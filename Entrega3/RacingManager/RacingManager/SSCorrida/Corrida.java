package RacingManager.SSCorrida;

import RacingManager.SSCarro.*;
import RacingManager.*;

import java.sql.Array;
import java.util.*;

public class Corrida {

	private int id;
	private int voltas;
	private List<Jogador> posicao = new ArrayList<>();
	private Map<String, List<Long>> tempo = new HashMap<>();
	private String meteorologia;
	private Circuito circuito;
	private Map<String, List<Jogador>> posCategoria = new HashMap<>();

	public Corrida(){
		this.voltas = 5;
		this.posicao = new ArrayList<>();
		this.tempo = new HashMap<>();
		this.meteorologia = "Seco";
		this.posCategoria = new HashMap<>();
	}

	public Corrida(Circuito circuito, int voltas){
		this.voltas = voltas;
		this.posicao = new ArrayList<>();
		this.tempo = new HashMap<>();
		this.meteorologia = "Seco";
		this.posCategoria = new HashMap<>();
		this.circuito = circuito;
	}

	public void setMeteorologia(){
		double randomDouble = Math.random();

		// If the random number is less than 0.5, return 0. Otherwise, return 1.
		if (randomDouble < 0.5) {
			this.meteorologia = "Chuva"; // Quando é 0
		} else {
			this.meteorologia =  "Seco"; // Quando é 1
		}
	}

	public void addParticipantes(Set<Jogador> jogadores){
		posicao.addAll(jogadores);
	}

	public String simulaCorrida(){
		List<Jogador> ultrapassar = new ArrayList<>();
		Map<Integer, List<String>> acontecimentos = new HashMap<>();

		posicaoInicial();

		Scanner scanner = new Scanner(System.in);

		int i = 1;
		while (i <= voltas) {
			System.out.println("Press Enter to simulate the next lap, or enter 'q' to quit: ");
			String input = scanner.nextLine();
			if (input.equals("q")) {
				break;
			}

			List<Elemento> elementos = circuito.getElementos();
			List<String> acontecimento = new ArrayList<>();
			int x = 0;

			for (Elemento e : elementos){
				x++;
				String GDU = e.getGDU();

				for (int j = 1; j < posicao.size(); j++){
					Jogador jc = posicao.get(j);
					Carro c = jc.getCarro();

					if(!c.getDNF()) {
						boolean res = c.tentaUltrapassar(GDU);
						if (res) { ultrapassar.add(jc); }
					}
				}

				for(Jogador u : ultrapassar){
					Carro uc = u.getCarro();
					boolean dnf = uc.calculaDNF(i / voltas, GDU, meteorologia);

					if(dnf){
						uc.setDNF(true);
						acontecimento.add(adicionarAcontecimento(u, e, "DNF", x));
					}
					else{
						boolean ovr = false;
						int ind = posicao.indexOf(u);

						if (ind != 0)
							ovr = uc.calculaOverall(posicao.get(ind - 1).getCarro());

						if(ovr){
							updatePosicao(u);
							acontecimento.add(adicionarAcontecimento(u,e,"Ultrapassagem",x));
						}
					}
				}

				ultrapassar.clear();
			}
			acontecimentos.put(i, acontecimento);
			printResumo(i, acontecimentos.get(i));
			acontecimento.clear();

			i++;
		}
		clearDNF();

		return resumoCorrida();
	}

	private void updatePosicao(Jogador u){
		int ind = posicao.indexOf(u);
		Collections.swap(posicao, ind, ind - 1);
	}

	private void posicaoInicial(){
		System.out.println("----Posição Inicial----");
		for (int i = 1; i <= posicao.size(); i++){
			System.out.println(i + "º: " + posicao.get(i-1).getNomeJogador());
		}
	}

	private String adicionarAcontecimento(Jogador u, Elemento e, String acontecimento, int numero){
		if (Objects.equals(acontecimento, "DNF")){
			return "O Jogador " + u.getNomeJogador() + " despistou-se na " + e.getCategoria() + " " + numero + "\n";
		}
		else if (Objects.equals(acontecimento, "Ultrapassagem")){
			return "O Jogador " + u.getNomeJogador() + " fez uma ultrapassagem com sucesso na " + e.getCategoria() + " " + numero +  " e passou para " + (posicao.indexOf(u) + 1) + "\n";
		}
		else
			return null;
	}

	private void printResumo(int volta, List<String> acontecimentos){
		System.out.println("---------- Volta " + volta + " ----------");
		if(acontecimentos != null)
			System.out.println(acontecimentos);
		System.out.println("------------------------------");
	}

	private String resumoCorrida(){
		StringBuilder sb = new StringBuilder();

		for(int i = 1; i <= posicao.size(); i++){
			sb.append(i + "º ---> " + posicao.get(i-1).getNomeJogador());
			sb.append("\n");
		}

		posicaoCategoria();

		for (String categoria : posCategoria.keySet()) {
			sb.append(categoria);
			sb.append(": ");
			for (Jogador jogador : posCategoria.get(categoria)) {
				sb.append(jogador.getNomeJogador());
				sb.append(", ");
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	private void posicaoCategoria(){
		for (Jogador jogador : posicao) {
			String categoria = jogador.getCarro().getCategoria();
			if (!posCategoria.containsKey(categoria)) {
				posCategoria.put(categoria, new ArrayList<>());
			}
			posCategoria.get(categoria).add(jogador);
		}
	}

	private void clearDNF(){
		for(Jogador j : posicao){
			j.getCarro().setDNF(false);
		}
	}

	public int getVoltas() {
		return voltas;
	}

	public void setVoltas(int voltas) {
		this.voltas = voltas;
	}

	public List<Jogador> getPosicao() {
		return posicao;
	}

	public void setPosicao(List<Jogador> posicao) {
		this.posicao = posicao;
	}

	public Map<String, List<Long>> getTempo() {
		return tempo;
	}

	public void setTempo(Map<String, List<Long>> tempo) {
		this.tempo = tempo;
	}

	public String getMeteorologia() {
		return meteorologia;
	}

	public void setMeteorologia(String meteorologia) {
		this.meteorologia = meteorologia;
	}

	public Circuito getCircuito() {
		return circuito;
	}

	public void setCircuito(Circuito circuito) {
		this.circuito = circuito;
	}

	public Map<String, List<Jogador>> getPosCategoria() {
		return posCategoria;
	}

	public void setPosCategoria(Map<String, List<Jogador>> posCategoria) {
		this.posCategoria = posCategoria;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}