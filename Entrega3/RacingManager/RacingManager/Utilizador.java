package RacingManager;

public class Utilizador {

	private String username;
	private String password;
	private int ranking;

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