package RacingManager.SSCarro;

import RacingManager.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Carro {

	private String idCarro;
	private int voltas;
	private List<Integer> posicao;
	private List<Long> tempo;
	private int fiabilidade;
	private int potencia;
	private int cilindrada;
	private float PAC;
	private String pneus;
	private String motor;
	private float downforce;
	private String categoria;
	private Boolean DNF;
	private String marca;
	private String modelo;
	private String id;

	private Piloto piloto;

	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

	public String getIdCarro() {
		return idCarro;
	}

	public void setIdCarro(String idCarro) {
		this.idCarro = idCarro;
	}

	public boolean tentaUltrapassar(String gdu) {
		boolean response = false;
		float SVA1 = piloto.getSVA();

		float value = (SVA1 + randomFloat()) / 2;

		if (value >= 0.5)
			response = true;

		return response;
	}

	public boolean calculaDNF(float v, String gdu, String meteorologia) {
		boolean response = false;
		float e = 0;
		float cts = piloto.getCTS();
		float c = calculaMeteorologia(meteorologia);

		if(Objects.equals(gdu, "Reta"))
			e = 0.25F;
		else if(Objects.equals(gdu, "Curva"))
			e = 0.5F;
		else
			e = 0.75F;

		if(Objects.equals(meteorologia, "Seco"))
			cts = 1 - cts;

		float value = (e + v + c + cts + (4 * randomFloat())) / 8;

		if (value >= 0.5)
			response = true;

		return response;
	}

	public float calculaMeteorologia(String meteorologia){
		float p = 0;
		float m = 0;

		if (Objects.equals(meteorologia, "Chuva") && (Objects.equals(pneus, "Macio") || Objects.equals(pneus, "Duro")))
			p = 1;
		else if(Objects.equals(meteorologia, "Chuva"))
			p = 0;
		else{
			if (Objects.equals(pneus, "Macio"))
				p = 0.75F;
			else
				p = 0.25F;
		}

		if (Objects.equals(motor, "Conservador"))
			p = 0.25F;
		else if (Objects.equals(motor, "Normal"))
			p = 0.5F;
		else
			p = 0.75F;

		return (p + m + (1 - PAC) + (1 - downforce));
	}
	public boolean calculaOverall(Carro frente) {
		boolean response = false;
		float c1, c2, p1, p2, m1, m2, d1, d2, pac1, pac2 = 0;
		d1 = getDownforce();
		d2 = frente.getDownforce();
		pac1 = getPAC();
		pac2 = frente.getPAC();

		String categoria1 = getCategoria();
		String categoria2 = frente.getCategoria();
		String pneus1 = getPneus();
		String pneus2 = frente.getPneus();
		String motor1 = getMotor();
		String motor2 = frente.getMotor();

		if (Objects.equals(categoria1, "C1"))
			c1 = 1;
		else if (Objects.equals(categoria1, "C2"))
			c1 = 0.75F;
		else if (Objects.equals(categoria1, "GT"))
			c1 = 0.5F;
		else
			c1 = 0.25F;

		if (Objects.equals(categoria2, "C1"))
			c2 = 1;
		else if (Objects.equals(categoria2, "C2"))
			c2 = 0.75F;
		else if (Objects.equals(categoria2, "GT"))
			c2 = 0.5F;
		else
			c2 = 0.25F;

		if (Objects.equals(pneus1, "Macio"))
			p1 = 0.75F;
		else if (Objects.equals(pneus1, "Duro"))
			p1 = 0.5F;
		else
			p1 = 0.25F;

		if (Objects.equals(pneus2, "Macio"))
			p2 = 0.75F;
		else if (Objects.equals(pneus2, "Duro"))
			p2 = 0.5F;
		else
			p2 = 0.25F;

		if (Objects.equals(motor1, "Agressivo"))
			m1 = 0.75F;
		else if (Objects.equals(motor1, "Normal"))
			m1 = 0.5F;
		else
			m1 = 0.25F;

		if (Objects.equals(motor2, "Agressivo"))
			m2 = 0.75F;
		else if (Objects.equals(motor2, "Normal"))
			m2 = 0.5F;
		else
			m2 = 0.25F;

		float val1 = (c1 / 2) + ((p1 + m1 + d1 + pac1) / 4);
		float val2 = (c2 / 2) + ((p2 + m2 + d2 + pac2) / 4);

		if (val1 > val2)
			response = true;

		return response;
	}
	public long somaTempo(){
		long sum = 0;
		for (Long l : tempo){
			sum += l;
		}

		return sum;
	}
	private float randomFloat(){
		Random rng = new Random();  // create a new random number generator

		return rng.nextFloat();
	}

	public int getVoltas() {
		return voltas;
	}

	public void setVoltas(int voltas) {
		this.voltas = voltas;
	}

		public List<Integer> getPosicao() {
		return posicao;
	}

	public void setPosicao(List<Integer> posicao) {
		this.posicao = posicao;
	}

	public List<Long> getTempo() {
		return tempo;
	}

	public void setTempo(List<Long> tempo) {
		this.tempo = tempo;
	}

	public int getFiabilidade() {
		return fiabilidade;
	}

	public void setFiabilidade(int fiabilidade) {
		this.fiabilidade = fiabilidade;
	}

	public int getPotencia() {
		return potencia;
	}

	public void setPotencia(int potencia) {
		this.potencia = potencia;
	}

	public int getCilindrada() {
		return cilindrada;
	}

	public void setCilindrada(int cilindrada) {
		this.cilindrada = cilindrada;
	}

	public Float getPAC() {
		return PAC;
	}

	public void setPAC(Float PAC) {
		this.PAC = PAC;
	}

	public String getPneus() {
		return pneus;
	}

	public void setPneus(String pneus) {
		this.pneus = pneus;
	}

	public String getMotor() {
		return motor;
	}

	public void setMotor(String motor) {
		this.motor = motor;
	}

	public Float getDownforce() {
		return downforce;
	}

	public void setDownforce(Float downforce) {
		this.downforce = downforce;
	}

/*	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}*/

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Boolean getDNF() {
		return DNF;
	}

	public void setDNF(Boolean DNF) {
		this.DNF = DNF;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void afinaCarro(){

	}

	@Override
	public String toString() {
		return "Carro{" +
				"voltas=" + voltas +
				", posicao=" + posicao +
				", tempo=" + tempo +
				", fiabilidade=" + fiabilidade +
				", potencia=" + potencia +
				", cilindrada=" + cilindrada +
				", PAC=" + PAC +
				", pneus='" + pneus + '\'' +
				", motor='" + motor + '\'' +
				", downforce=" + downforce +
				// ", piloto=" + piloto +
				", categoria='" + categoria + '\'' +
				", DNF=" + DNF +
				", marca='" + marca + '\'' +
				", modelo='" + modelo + '\'' +
				'}';
	}
}