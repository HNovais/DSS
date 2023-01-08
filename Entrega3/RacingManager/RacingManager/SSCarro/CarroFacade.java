package RacingManager.SSCarro;

import data.CarroDAO;

import java.util.HashMap;
import java.util.Map;

public class CarroFacade implements ICarro {

	CarroDAO carros;
	//private Map<String, Carro> carros;

	public CarroFacade() {
		//this.carros = CarroDAO.getInstance();
	}


/*
	/**
	 * 
	 * @param val
	 */

/*	public void alteraDownforce(float val) {
		if (val < 0 || val > 1) {
			throw new IllegalArgumentException("Invalid downforce value: " + val);
		}
		Carro carro = carros.get(idCarro);
		if (carro == null) {
			throw new IllegalArgumentException("Invalid car id: " + idCarro);
		}
		carro.setDownforce(val);
	}


	/**
	 * 
	 * @param p
	 */

	// Tipos de Pneus disponiveis: Duro / Macio / Chuva
/*	public void escolhePneus(String p) {
		Carro carro = carros.get(idCarro);
		if (carro == null) {
			throw new IllegalArgumentException("Invalid car id: " + idCarro);
		}
		if (!p.equals("Macio") && !p.equals("Chuva") && !p.equals("Duro")) {
			throw new IllegalArgumentException("Invalid tire type: " + p);
		}
		carro.setPneus(p);
	}


	/**
	 * 
	 * @param m
	 */
	// Tipos de disponiveis: Conservador, Normal ou Agressivo
/*	public void escolheMotor(String idCarro, String m) {
		Carro carro = carros.get(idCarro);
		if (carro == null) {
			throw new IllegalArgumentException("Invalid car id: " + idCarro);
		}
		if (!m.equals("Conservador") && !m.equals("Normal") && !m.equals("Agressivo")) {
			throw new IllegalArgumentException("Invalid engine type: " + m);
		}
		carro.setMotor(m);
	}
*/
	public Carro getCarro(String idCarro) {
		return this.carros.get(idCarro);
	}

	public CarroDAO getCarros() {
		return carros;
	}

	public void setCarros(CarroDAO carros) {
		this.carros = carros;
	}

	/**
	 * 
	 * @param GDU
	 */
	public boolean tentaUltrapassar(String GDU) {
		// TODO - implement CarroFacade.tentaUltrapassar
		throw new UnsupportedOperationException();
	}

	public boolean calculaDNF() {
		// TODO - implement CarroFacade.calculaDNF
		throw new UnsupportedOperationException();
	}

	public int calculaOverall() {
		// TODO - implement CarroFacade.calculaOverall
		throw new UnsupportedOperationException();
	}

	public long calculaTempo() {
		// TODO - implement CarroFacade.calculaTempo
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param cat1
	 * @param cat2
	 */
	public boolean tentaUltrapassarPremium(String cat1, String cat2) {
		// TODO - implement CarroFacade.tentaUltrapassarPremium
		throw new UnsupportedOperationException();
	}


}