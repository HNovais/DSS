package RacingManager;

import RacingManager.SSCarro.*;

public class Jogador {

	private String id;
	private Carro carro;
	private Piloto piloto;
	private int afinacoes;

	/**
	 * 
	 * @param ca
	 */
	public void escolheCarro(Carro ca) {
		this.carro = ca;
	}

	/**
	 *
	 * @param p
	 */
	public void escolhePiloto(Piloto p){
		setPiloto(p);
	}

	/**
	 * 
	 * @param m
	 */
	public void escolheMotor(String m) {
		carro.setMotor(m);
	}

	/**
	 * 
	 * @param p
	 */
	public void escolhePneus(String p) {
		carro.setPneus(p);
	}

	/**
	 * 
	 * @param a
	 */
	public String indicaAfinacoes(int a) {
		int total = a - afinacoes;
		return String.valueOf(total);
	}

	/**
	 * 
	 * @param a
	 */
	public boolean verificaAfinacoes(int a){
		if(afinacoes - a > 0)
			return true;
		return false;
	}

	public void afinaCarro() {
		// TODO - implement Jogador.afinaCarro
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param val
	 */
	public void alteraDownforce(float val){
		carro.setDownforce(val);
	}

	public Jogador(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}
}