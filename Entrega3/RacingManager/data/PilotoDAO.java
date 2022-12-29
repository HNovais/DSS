package data;

import RacingManager.Piloto;

import java.sql.*;

public class PilotoDAO {
    private static PilotoDAO instance;
    private Connection connection;

    private PilotoDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE pilots " +
                    "(nomePiloto TEXT PRIMARY KEY, " +
                    "SVA REAL NOT NULL, " +
                    "CTS REAL NOT NULL)";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static PilotoDAO getInstance() {
        if (instance == null) {
            instance = new PilotoDAO();
        }
        return instance;
    }

    public void put(Piloto piloto) {
        // Add a new pilot to the database
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO pilots (nomePiloto, SVA, CTS) VALUES (?, ?, ?)");
            statement.setString(1, piloto.getNomePiloto());
            statement.setFloat(2, piloto.getSVA());
            statement.setFloat(3, piloto.getCTS());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Piloto get(String nomePiloto) {
        // Retrieve a pilot from the database by their name
        Piloto piloto = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM pilots WHERE nomePiloto = ?");
            statement.setString(1, nomePiloto);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                piloto = new Piloto();
                piloto.setNomePiloto(resultSet.getString("nomePiloto"));
                piloto.setSVA(resultSet.getFloat("SVA"));
                piloto.setCTS(resultSet.getFloat("CTS"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return piloto;
    }

    public void remove(String nomePiloto) {
        // Delete a pilot from the database
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM pilots WHERE nomePiloto = ?");
            statement.setString(1, nomePiloto);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int size() {
        // Return the number of pilots in the database
        int size = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM pilots");
            if (resultSet.next()) {
                size = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return size;
    }

    public void update(Piloto piloto) {
        // Update an existing pilot in the database
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE pilots SET SVA = ?, CTS = ? WHERE nomePiloto = ?");
            statement.setFloat(1, piloto.getSVA());
            statement.setFloat(2, piloto.getCTS());
            statement.setString(3, piloto.getNomePiloto());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


