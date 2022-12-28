package RacingManager.SSCarro;

import RacingManager.*;

import java.util.List;
import java.util.Map;

public class Carro {

	private int voltas;
	private List<Elemento> elementos;
	private List<Carro> posicao;
	private List<Long> tempo;
	private int fiabilidade;
	private int potencia;
	private int cilindrada;
	private Float PAC;
	private String pneus;
	private String motor;
	private Float downforce;
	private Piloto piloto;
	private String categoria;
	private Boolean DNF;
	private String marca;
	private String modelo;
	private String id;

	public boolean tentaUltrapassar(String GDU, Carro frente){
		// Alguém que faça esta merda

		return false;
	}

	public boolean calculaDNF() {
		//Outra

		return false;
	}

	public boolean calculaOverall() {
		//Mais Uma malta

		return false;
	}

	public void calculaTempo() {
		// Boa Sorte
		// Depois de calcular meter na lista de tempo :)
	}

	public long somaTempo(){
		long sum = 0;
		for (Long l : tempo){
			sum += l;
		}

		return sum;
	}

	public int getVoltas() {
		return voltas;
	}

	public void setVoltas(int voltas) {
		this.voltas = voltas;
	}

	public List<Elemento> getElementos() {
		return elementos;
	}

	public void setElementos(List<Elemento> elementos) {
		this.elementos = elementos;
	}

	public List<Carro> getPosicao() {
		return posicao;
	}

	public void setPosicao(List<Carro> posicao) {
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

	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

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

	@Override
	public String toString() {
		return "Carro{" +
				"voltas=" + voltas +
				", elementos=" + elementos +
				", posicao=" + posicao +
				", tempo=" + tempo +
				", fiabilidade=" + fiabilidade +
				", potencia=" + potencia +
				", cilindrada=" + cilindrada +
				", PAC=" + PAC +
				", pneus='" + pneus + '\'' +
				", motor='" + motor + '\'' +
				", downforce=" + downforce +
				", piloto=" + piloto +
				", categoria='" + categoria + '\'' +
				", DNF=" + DNF +
				", marca='" + marca + '\'' +
				", modelo='" + modelo + '\'' +
				'}';
	}
}