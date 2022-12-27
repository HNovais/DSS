package RacingManager.SSCarro;

public interface ICarro {

	void afinaCarro();

	/**
	 * 
	 * @param val
	 */
	void alteraDownforce(String idCarro, float val);

	/**
	 * 
	 * @param p
	 */
	void escolhePneus(String idCarro, String p);

	/**
	 * 
	 * @param m
	 */
	void escolheMotor(String idCarro, String m);

	/**
	 * 
	 * @param GDU
	 */
	boolean tentaUltrapassar(String GDU);

	boolean calculaDNF();

	int calculaOverall();

	long calculaTempo();

	/**
	 * 
	 * @param cat1
	 * @param cat2
	 */
	boolean tentaUltrapassarPremium(String cat1, String cat2);

}