package RacingManager.SSCarro;

import RacingManager.Piloto;
import data.CarroDAO;

import java.util.List;
import java.util.Objects;

public interface ICarro {

	public Carro getCarro(String idCarro);
	public boolean tentaUltrapassar(String GDU);

	public boolean calculaDNF();

	public int calculaOverall();

	public long calculaTempo();

	public boolean tentaUltrapassarPremium(String cat1, String cat2);

	public CarroDAO getCarros();

	public void setCarros(CarroDAO carros);
}