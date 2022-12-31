package RacingManager;

import RacingManager.SSCarro.*;
import java.util.Random;

public class Jogador {

	private String id;
	public Carro carro;
	private Piloto piloto;
	private int afinacoes;

	public Jogador() {
		this.id = "";
		this.carro = null;
		this.piloto = null;
		this.afinacoes = 0;
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public int getAfinacoes() {
		return afinacoes;
	}

	public void setAfinacoes(int afinacoes) {
		this.afinacoes = afinacoes;
	}

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
			this.carro.afinaCarro();
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