package data;

import RacingManager.Utilizador;

import java.sql.DriverManager;
import java.sql.*;

public class UtilizadorDAO {
    private static UtilizadorDAO singleton = null;

    private UtilizadorDAO() {
        try (Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS users " +
                    "(username varchar(45) PRIMARY KEY, " +
                    "password varchar(45) NOT NULL, " +
                    "ranking INTEGER NOT NULL)";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }


    public static UtilizadorDAO getInstance() {
        if (UtilizadorDAO.singleton == null) singleton = new UtilizadorDAO();
        return singleton;
    }

    public void put(Utilizador utilizador) {
        // Add a new user to the database
        try (Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (username, password, ranking) VALUES (?, ?, ?)");
            statement.setString(1, utilizador.getUsername());
            statement.setString(2, utilizador.getPassword());
            statement.setInt(3, utilizador.getRanking());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Utilizador get(String username) {
        // Retrieve a user from the database by their username
        Utilizador utilizador = null;
        try (Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                utilizador = new Utilizador();
                utilizador.setUsername(resultSet.getString("username"));
                utilizador.setPassword(resultSet.getString("password"));
                utilizador.setRanking(resultSet.getInt("ranking"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilizador;
    }

    public void remove(String username) {
        // Delete a user from the database
        try (Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM users WHERE username = ?");
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int size() {
        // Return the number of users in the database
        int size = 0;
        try (Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users");
            if (resultSet.next()) {
                size = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return size;
    }

    public void update(Utilizador utilizador) {
        // Update an existing user in the database
        try (Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE users SET password = ?, ranking = ? WHERE username = ?");
            statement.setString(1, utilizador.getPassword());
            statement.setInt(2, utilizador.getRanking());
            statement.setString(3, utilizador.getUsername());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}