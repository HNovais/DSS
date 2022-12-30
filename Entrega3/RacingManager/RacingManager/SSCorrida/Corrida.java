package RacingManager.SSCorrida;

import RacingManager.SSCarro.*;
import RacingManager.*;

import java.util.*;

public class Corrida {

	private int voltas;
	private List<Carro> posicao = new ArrayList<>();
	private Map<String, List<Long>> tempo = new HashMap<>();
	private String meteorologia;
	private Circuito circuito;

	private Map<String, List<Carro>> posCategoria = new HashMap<>();

	public String simulaCorrida(){
		List<Carro> ultrapassar = new ArrayList<>();
		StringBuilder acontecimentos = new StringBuilder();

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
						acontecimentos.append(adicionarAcontecimento(u, e, "DNF", x));
					}
					else{
						boolean ovr = false;
						int ind = posicao.indexOf(u);

						if (ind != 0)
							ovr = u.calculaOverall(posicao.get(ind - 1));

						if(ovr){
							updatePosicao(u);
							acontecimentos.append(adicionarAcontecimento(u,e,"Ultrapassagem",x));
						}
					}
				}

				//for (Carro p : posicao){
					//p.carro.calculaTempo();
					//updateTempo(p);
				//}

				ultrapassar.clear();
			}

			printResumo(i, acontecimentos);

		}

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

	private void printResumo(int volta, StringBuilder acontecimentos){
		System.out.println("---------- Volta " + volta + " ----------");
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
}