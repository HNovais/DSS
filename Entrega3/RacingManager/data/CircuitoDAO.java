package data;

import RacingManager.Circuito;
import RacingManager.Elemento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CircuitoDAO {
    private static CircuitoDAO instance;
    private Connection connection;

    private CircuitoDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE circuits " +
                    "(nomeCircuito TEXT PRIMARY KEY, " +
                    "distância INTEGER NOT NULL)";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE elements " +
                    "(categoria TEXT NOT NULL, " +
                    "GDU TEXT NOT NULL, " +
                    "nomeCircuito TEXT NOT NULL, " +
                    "PRIMARY KEY (categoria, nomeCircuito), " +
                    "FOREIGN KEY (nomeCircuito) REFERENCES circuits(nomeCircuito))";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static CircuitoDAO getInstance() {
        if (instance == null) {
            instance = new CircuitoDAO();
        }
        return instance;
    }

    public void put(Circuito circuito) {
        // Add a new circuit and its elements to the database
        try {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO circuits (nomeCircuito, distância) VALUES (?, ?)");
            statement.setString(1, circuito.getNomeCircuito());
            statement.setInt(2, circuito.getDistancia());
            statement.executeUpdate();

            statement = connection.prepareStatement(
                    "INSERT INTO elements (categoria, GDU, nomeCircuito) VALUES (?, ?, ?)");
            for (Elemento elemento : circuito.getElementos()) {
                statement.setString(1, elemento.getCategoria());
                statement.setString(2, elemento.getGDU());
                statement.setString(3, circuito.getNomeCircuito());
                statement.addBatch();
            }
            statement.executeBatch();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Circuito get(String nomeCircuito) {
        // Retrieve a circuit and its elements from the database
        Circuito circuito = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM circuits WHERE nomeCircuito = ?");
            statement.setString(1, nomeCircuito);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                circuito = new Circuito();
                circuito.setNomeCircuito(resultSet.getString("nomeCircuito"));
                circuito.setDistancia(resultSet.getInt("distância"));

                statement = connection.prepareStatement(
                        "SELECT * FROM elements WHERE nomeCircuito = ?");
                statement.setString(1, nomeCircuito);
                resultSet = statement.executeQuery();
                List<Elemento> elementos = new ArrayList<>();
                while (resultSet.next()) {
                    Elemento elemento = new Elemento();
                    elemento.setCategoria(resultSet.getString("categoria"));
                    elemento.setGDU(resultSet.getString("GDU"));
                    elementos.add(elemento);
                }
                circuito.setElementos(elementos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return circuito;
    }

    public void remove(String nomeCircuito) {
        // Delete a circuit and its elements from the database
        try {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM elements WHERE nomeCircuito = ?");
            statement.setString(1, nomeCircuito);
            statement.executeUpdate();

            statement = connection.prepareStatement(
                    "DELETE FROM circuits WHERE nomeCircuito = ?");
            statement.setString(1, nomeCircuito);
            statement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int size() {
        // Return the number of circuits in the database
        int size = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM circuits");
            if (resultSet.next()) {
                size = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return size;
    }

    public void update(Circuito circuito) {
        // Update a circuit and its elements in the database
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE circuits SET distância = ? WHERE nomeCircuito = ?");
            statement.setInt(1, circuito.getDistancia());
            statement.setString(2, circuito.getNomeCircuito());
            statement.executeUpdate();

            statement = connection.prepareStatement(
                    "DELETE FROM elements WHERE nomeCircuito = ?");
            statement.setString(1, circuito.getNomeCircuito());
            statement.executeUpdate();

            statement = connection.prepareStatement(
                    "INSERT INTO elements (categoria, GDU, nomeCircuito) VALUES (?, ?, ?)");
            for (Elemento elemento : circuito.getElementos()) {
                statement.setString(1, elemento.getCategoria());
                statement.setString(2, elemento.getGDU());
                statement.setString(3, circuito.getNomeCircuito());
                statement.addBatch();
            }
            statement.executeBatch();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

