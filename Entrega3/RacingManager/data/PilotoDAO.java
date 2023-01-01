package data;

import RacingManager.Piloto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PilotoDAO {
    private static PilotoDAO singleton = null;

    private PilotoDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement statement = conn.createStatement()) {
            String sql = "CREATE TABLE if not exists pilot " +
                    "(nomePiloto VARCHAR(100) PRIMARY KEY, " +
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
        if (singleton == null) {
            singleton = new PilotoDAO();
        }
        return singleton;
    }

    public void put(Piloto piloto) {
        // Add a new pilot to the database
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)) {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO pilot (nomePiloto, SVA, CTS) VALUES (?, ?, ?)");
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
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)) {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT * FROM pilot WHERE nomePiloto = ?");
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
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)) {
            PreparedStatement statement = conn.prepareStatement(
                    "DELETE FROM pilot WHERE nomePiloto = ?");
            statement.setString(1, nomePiloto);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int size() {
        // Return the number of pilots in the database
        int size = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM pilot");
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
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)) {
            PreparedStatement statement = conn.prepareStatement(
                    "UPDATE pilot SET SVA = ?, CTS = ? WHERE nomePiloto = ?");
            statement.setFloat(1, piloto.getSVA());
            statement.setFloat(2, piloto.getCTS());
            statement.setString(3, piloto.getNomePiloto());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Piloto> getAll() {
        List<Piloto> pilots = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM piloto");
            while (resultSet.next()) {
                Piloto piloto = new Piloto();
                piloto.setNomePiloto(resultSet.getString("nomePiloto"));
                piloto.setSVA(resultSet.getFloat("SVA"));
                piloto.setCTS(resultSet.getFloat("CTS"));
                pilots.add(piloto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pilots;
    }

}


