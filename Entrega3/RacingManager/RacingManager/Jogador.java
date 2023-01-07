package RacingManager;

import RacingManager.SSCarro.*;
import java.util.Random;

public class Jogador {

	private String nomeJogador;
	private int id;
	public Carro carro;
	private Piloto piloto;
	private int afinacoes;

	public Jogador(String nome) {
		this.nomeJogador = nome;
		this.id = 0;
		this.carro = null;
		this.piloto = null;
		this.afinacoes = 0;
	}

	public Jogador(String nomeJogador2, int id2) {
		this.nomeJogador = nomeJogador2;
		this.id = id2;
		this.carro = null;
		this.piloto = null;
		this.afinacoes = 0;
	}

	public Jogador(int afinacoes) {
		this.nomeJogador = "";
		this.id = -1;
		this.carro = null;
		this.piloto = null;
		this.afinacoes = afinacoes;
	}

	public String getNomeJogador() {
		return nomeJogador;
	}

	public void setNomeJogador(String nomeJogador) {
		this.nomeJogador = nomeJogador;
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

	// Get e Set downforce do carro quando o jogador faz afinações.

	public float getDownforce(){ return this.carro.getDownforce(); }
	public void setDownfore(float downforce) { this.carro.setDownforce(downforce); }

	public void efetuaAfinacao(){
		this.afinacoes--;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

	@Override
	public String toString() {
		return "Jogador{" +
				"nomeJogador='" + nomeJogador + '\'' +
				", id=" + id +
				", afinacoes=" + afinacoes +
				'}';
	}
}