package data;

import RacingManager.Circuito;
import RacingManager.SSCarro.Carro;
import RacingManager.SSCorrida.Corrida;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class CorridaDAO {
    private static CorridaDAO singleton;
    private Connection connection;

    {
        try {
            connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private CorridaDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement statement = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS races" +
                    "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                    "voltas INTEGER NOT NULL, " +
                    "posicao BLOB NOT NULL, " +
                    "tempo BLOB NOT NULL, " +
                    "meteorologia varchar(45) NOT NULL, " +
                    "circuito BLOB NOT NULL, " +
                    "posCategoria BLOB NOT NULL)";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            // Error creating table...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static CorridaDAO getInstance() {
        if (singleton == null) {
            singleton = new CorridaDAO();
        }
        return singleton;
    }


    public Corrida get(int id) {
        // Retrieve a race from the database by its id
        Corrida corrida = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM races WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                corrida = new Corrida();
                corrida.setId(resultSet.getInt("id"));
                corrida.setVoltas(resultSet.getInt("voltas"));
                corrida.setPosicao((List<Carro>) resultSet.getObject("posicao"));
                corrida.setTempo((Map<String, List<Long>>) resultSet.getObject("tempo"));
                corrida.setMeteorologia(resultSet.getString("meteorologia"));
                corrida.setCircuito((Circuito) resultSet.getObject("circuito"));
                corrida.setPosCategoria((Map<String, List<Carro>>) resultSet.getObject("posCategoria"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return corrida;
    }


    public void put(Corrida corrida) {
        // Add a new race to the database
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO races (voltas, posicao, tempo, meteorologia, circuito, posCategoria) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, corrida.getVoltas());
            statement.setObject(2, corrida.getPosicao());
            statement.setObject(3, corrida.getTempo());
            statement.setString(4, corrida.getMeteorologia());
            statement.setObject(5, corrida.getCircuito());
            statement.setObject(6, corrida.getPosCategoria());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(int id) {
        // Delete a race from the database
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM races WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int size() {
        // Return the number of races in the database
        int size = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM races");
            if (resultSet.next()) {
                size = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return size;
    }

    public void update(Corrida corrida) {
        // Update an existing race in the database
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE races SET voltas = ?, posicao = ?, tempo = ?, meteorologia = ?, circuito = ?, posCategoria = ? WHERE id = ?");
            statement.setInt(1, corrida.getVoltas());
            statement.setObject(2, corrida.getPosicao());
            statement.setObject(3, corrida.getTempo());
            statement.setString(4, corrida.getMeteorologia());
            statement.setObject(5, corrida.getCircuito());
            statement.setObject(6, corrida.getPosCategoria());
            statement.setInt(7, corrida.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}