package RacingManager;

public class Utilizador {

	private String username;
	private String password;
	private int ranking;

	public Utilizador() {
		this.username = "";
		this.password = "";
		this.ranking = 0;
	}

    public Utilizador(String username, String password) {
		this.username = username;
		this.password = password;
		this.ranking = 0;
    }

	// Getters and Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	@Override
	public String toString() {
		return "Utilizador{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", ranking=" + ranking +
				'}';
	}
}