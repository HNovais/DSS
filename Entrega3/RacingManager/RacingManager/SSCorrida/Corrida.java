package RacingManager.SSCorrida;

import RacingManager.SSCarro.*;
import RacingManager.*;

import java.util.*;

public class Corrida {

	private int voltas;
	private List<Jogador> posicao = new ArrayList<>();
	private Map<String, List<Long>> tempo = new HashMap<>();
	private String meteorologia;
	private Circuito circuito;
	private Map<String, List<Carro>> posCategoria;

	public String simulaCorrida(){
		List<Jogador> ultrapassar = new ArrayList<>();
		List<Jogador> DNF = new ArrayList<>();

		for (int i = 1; i <= voltas; i++){
			List<Elemento> elementos = circuito.getElementos();

			for (Elemento e : elementos){
				String GDU = e.getGDU();

				for (int j = 1; j < posicao.size(); j++){
					Jogador c = posicao.get(j);
					boolean res = c.tentaUltrapassar(GDU);

					if(res){ultrapassar.add(c);}
				}

				for(Jogador u : ultrapassar){
					boolean dnf = u.calculaDNF(i / voltas, GDU, meteorologia);

					if(dnf){
						u.carro.setDNF(true);
						DNF.add(u);
					}
					else{
						boolean ovr = false;
						int ind = posicao.indexOf(u);

						if (ind != 0)
							u.calculaOverall(posicao.get(ind - 1));

						if(ovr){updatePosicao(u);}
					}
				}

				for (Jogador p : posicao){
					//p.carro.calculaTempo();
					updateTempo(p);
				}
			}

			printResumo();
		}

		return resumoCorrida();
	}

	private void updatePosicao(Jogador u){
		int ind = posicao.indexOf(u);
		Collections.swap(posicao, ind, ind - 1);
	}

	private void updateTempo(Jogador p){
		List<Long> list = tempo.get(p.getId());
		list.add(p.carro.somaTempo());
	}

	private void printResumo(){
		//Tentativa de Ultrapassagem
		//Ultrapassagens
		//DNF
		//Tempo e Volta
	}

	private String resumoCorrida(){
		//Resumo da corrida

		return null;
	}
}