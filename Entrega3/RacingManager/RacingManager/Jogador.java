package RacingManager;

import RacingManager.SSCarro.*;
import java.util.Random;

public class Jogador {

	private String id;
	public Carro carro;
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

	public boolean tentaUltrapassar(String gdu) {
		boolean response = false;
		float SVA1 = piloto.getSVA();

		float value = (SVA1+randomFloat()) / 2;

		if (value >= 0.5)
			response = true;

		return response;
	}

	public boolean calculaDNF(float v, String gdu, String meteorologia) {
		boolean response = false;
		float e = 0;
		float cts = piloto.getCTS();
		float c = carro.calculaDNF(meteorologia);

		if(gdu == "Reta")
			e = 0.25F;
		else if(gdu == "Curva")
			e = 0.5F;
		else
			e = 0.75F;

		if(meteorologia == "Seco")
			cts = 1 - cts;

		float value = (e + v + c + cts + (4 * randomFloat())) / 8;

		if (value >= 0.5)
			response = true;

		return response;
	}

	public boolean calculaOverall(Jogador frente) {
		boolean response = false;
		float c1, c2, p1, p2, m1, m2, d1, d2, pac1, pac2 = 0;
		d1 = carro.getDownforce();
		d2 = frente.carro.getDownforce();
		pac1 = carro.getPAC();
		pac2 = frente.carro.getPAC();

		String categoria1 = carro.getCategoria();
		String categoria2 = frente.carro.getCategoria();
		String pneus1 = carro.getPneus();
		String pneus2 = frente.carro.getPneus();
		String motor1 = carro.getMotor();
		String motor2 = frente.carro.getMotor();

		if (categoria1 == "C1")
			c1 = 1;
		else if (categoria1 == "C2")
			c1 = 0.75F;
		else if (categoria1 == "GT")
			c1 = 0.5F;
		else
			c1 = 0.25F;

		if (categoria2 == "C1")
			c2 = 1;
		else if (categoria2 == "C2")
			c2 = 0.75F;
		else if (categoria2 == "GT")
			c2 = 0.5F;
		else
			c2 = 0.25F;

		if (pneus1 == "Macio")
			p1 = 0.75F;
		else if (pneus1 == "Duro")
			p1 = 0.5F;
		else
			p1 = 0.25F;

		if (pneus2 == "Macio")
			p2 = 0.75F;
		else if (pneus2 == "Duro")
			p2 = 0.5F;
		else
			p2 = 0.25F;

		if (motor1 == "Agressivo")
			m1 = 0.75F;
		else if (motor1 == "Normal")
			m1 = 0.5F;
		else
			m1 = 0.25F;

		if (motor2 == "Agressivo")
			m2 = 0.75F;
		else if (motor2 == "Normal")
			m2 = 0.5F;
		else
			m2 = 0.25F;

		float val1 = (c1 / 2) + ((p1 + m1 + d1 + pac1) / 4);
		float val2 = (c2 / 2) + ((p2 + m2 + d2 + pac2) / 4);

		if (val1 > val2)
			response = true;

		return response;
	}

	private float randomFloat(){
		Random rng = new Random();  // create a new random number generator
		float randomFloat = rng.nextFloat();

		return randomFloat;
	}
}