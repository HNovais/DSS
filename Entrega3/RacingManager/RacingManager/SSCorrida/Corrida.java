package RacingManager.SSCorrida;

import RacingManager.SSCarro.*;
import RacingManager.*;

import java.util.*;

public class Corrida {

	private int id;
	private int voltas;
	private List<Carro> posicao = new ArrayList<>();
	private Map<String, List<Long>> tempo = new HashMap<>();
	private String meteorologia;
	private Circuito circuito;
	private Map<String, List<Carro>> posCategoria = new HashMap<>();

	public Corrida(int id, Circuito circuito){
		this.id = 0;
		this.voltas = 5;
		this.posicao = new ArrayList<>();
		this.tempo = new HashMap<>();
		this.meteorologia = "Seco";
		this.circuito = circuito;
		this.posCategoria = new HashMap<>();
	}

	public Corrida(){
		this.voltas = 5;
		this.posicao = new ArrayList<>();
		this.tempo = new HashMap<>();
		this.meteorologia = "Seco";
		this.posCategoria = new HashMap<>();
	}

	public Corrida(Circuito circuito){
		this.voltas = 5;
		this.posicao = new ArrayList<>();
		this.tempo = new HashMap<>();
		this.meteorologia = "Seco";
		this.posCategoria = new HashMap<>();
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
		for(Jogador j : jogadores){
			posicao.add(j.getCarro());
		}
	}

	public String simulaCorrida(){
		List<Carro> ultrapassar = new ArrayList<>();
		Map<Integer, String> acontecimentos = new HashMap<>();

		for (int i = 1; i <= voltas; i++){
			List<Elemento> elementos = circuito.getElementos();
			int x = 0;

			for (Elemento e : elementos){
				x++;
				String GDU = e.getGDU();

				for (int j = 1; j < posicao.size(); j++){
					Carro c = posicao.get(j);

					if(!c.getDNF()) {
						boolean res = c.tentaUltrapassar(GDU);
						if (res) { ultrapassar.add(c); }
					}
				}

				for(Carro u : ultrapassar){
					boolean dnf = u.calculaDNF(i / voltas, GDU, meteorologia);

					if(dnf){
						u.setDNF(true);
						acontecimentos.put(i,adicionarAcontecimento(u, e, "DNF", x));
					}
					else{
						boolean ovr = false;
						int ind = posicao.indexOf(u);

						if (ind != 0)
							ovr = u.calculaOverall(posicao.get(ind - 1));

						if(ovr){
							updatePosicao(u);
							acontecimentos.put(i,adicionarAcontecimento(u,e,"Ultrapassagem",x));
						}
					}
				}

				//for (Carro p : posicao){
					//p.carro.calculaTempo();
					//updateTempo(p);
				//}

				ultrapassar.clear();
			}

			printResumo(i, acontecimentos.get(i));

		}
		clearDNF();

		return resumoCorrida();
	}

	private void updatePosicao(Carro u){
		int ind = posicao.indexOf(u);
		Collections.swap(posicao, ind, ind - 1);
	}

	private void updateTempo(Carro p){
		List<Long> list = tempo.get(p.getId());
		list.add(p.somaTempo());
	}

	private String adicionarAcontecimento(Carro u, Elemento e, String acontecimento, int numero){
		if (Objects.equals(acontecimento, "DNF")){
			return "O piloto " + u.getPiloto().getNomePiloto() + " despistou-se na " + e.getCategoria() + " " + numero + "\n";
		}
		else if (Objects.equals(acontecimento, "Ultrapassagem")){
			return "O piloto " + u.getPiloto().getNomePiloto() + " fez uma ultrapassagem com sucesso na " + e.getCategoria() + " " + numero +  " e passou para " + (posicao.indexOf(u) + 1) + "\n";
		}
		else
			return null;
	}

	private void printResumo(int volta, String acontecimentos){
		System.out.println("---------- Volta " + volta + " ----------");
		if(acontecimentos != null)
			System.out.println(acontecimentos);
		System.out.println("------------------------------");
	}

	private String resumoCorrida(){
		StringBuilder sb = new StringBuilder();

		for(int i = 1; i <= posicao.size(); i++){
			sb.append(i + "º ---> " + posicao.get(i-1));
			sb.append("\n");
		}

		posicaoCategoria();

		for (String categoria : posCategoria.keySet()) {
			sb.append(categoria);
			sb.append(": ");
			for (Carro carro : posCategoria.get(categoria)) {
				sb.append(carro.getPiloto().getNomePiloto());
				sb.append(", ");
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	private void posicaoCategoria(){
		for (Carro carro : posicao) {
			String categoria = carro.getCategoria();
			if (!posCategoria.containsKey(categoria)) {
				posCategoria.put(categoria, new ArrayList<>());
			}
			posCategoria.get(categoria).add(carro);
		}
	}

	private void clearDNF(){
		for(Carro c : posicao){
			c.setDNF(false);
		}
	}

	public int getVoltas() {
		return voltas;
	}

	public void setVoltas(int voltas) {
		this.voltas = voltas;
	}

	public List<Carro> getPosicao() {
		return posicao;
	}

	public void setPosicao(List<Carro> posicao) {
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

	public Map<String, List<Carro>> getPosCategoria() {
		return posCategoria;
	}

	public void setPosCategoria(Map<String, List<Carro>> posCategoria) {
		this.posCategoria = posCategoria;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}