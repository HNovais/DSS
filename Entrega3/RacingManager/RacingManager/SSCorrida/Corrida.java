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
	private Map<String, List<Carro>> posCategoria;

	public String simulaCorrida(){
		List<Carro> ultrapassar = new ArrayList<>();
		List<Carro> DNF = new ArrayList<>();

		for (int i = 1; i <= voltas; i++){
			List<Elemento> elementos = circuito.getElementos();

			for (Elemento e : elementos){
				String GDU = e.getGDU();

				for (int j = 1; j < posicao.size(); j++){
					Carro c = posicao.get(j);
					boolean res = c.tentaUltrapassar(GDU, posicao.get(j-1));

					if(res){ultrapassar.add(c);}
				}

				for(Carro u : ultrapassar){
					boolean dnf = u.calculaDNF();

					if(dnf){
						u.setDNF(true);
						DNF.add(u);
					}
					else{
						boolean ovr = u.calculaOverall();

						if(ovr){updatePosicao(u);}
					}
				}

				for (Carro p : posicao){
					p.calculaTempo();
					updateTempo(p);
				}
			}

			printResumo();
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