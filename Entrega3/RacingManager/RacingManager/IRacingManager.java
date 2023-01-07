package RacingManager;

import RacingManager.SSCampeonato.CampeonatoFacade;
import RacingManager.SSCarro.Carro;
import RacingManager.SSCarro.CarroFacade;
import RacingManager.SSCorrida.Corrida;
import data.CircuitoDAO;
import data.PilotoDAO;
import data.UtilizadorDAO;

import java.util.List;

public interface IRacingManager {

    public void escolhePneus(String idJ, String idCamp, String p);
    public void afinaCarro(String idCamp, String idJ);
    public void alteraDownforce(String idCamp, String idJ, float val);
    public void escolheCarro(String idJ, String idCarro, String idCamp);
    public void escolheMotor(String idJ, String idCamp, String m);
    public void escolhePiloto(String idJ, String idP, String idCamp);
    public String indicaAfinacoes(String idCamp, String idJ);
    public Corrida indicaCorrida(String idCamp);
    public int indicaMeteorologia(String idCamp);
    public void nomeJogadores(List<String> list);
    public void novoCampeonato(String idCamp);
    public void numeroJogadores(int n);
    public String simulaCorrida(String idCamp);
    public boolean verificaAfinacoes(String idCamp, String idJ);
    public void setUtilizadores(UtilizadorDAO utilizadores);
    public UtilizadorDAO getUtilizadores();
    public CampeonatoFacade getCampF();
    public PilotoDAO getPilotos();
    public void setPilotos(PilotoDAO pilotos);
    public CarroFacade getCarroF();
    public void setCarroF(CarroFacade carroF);
}