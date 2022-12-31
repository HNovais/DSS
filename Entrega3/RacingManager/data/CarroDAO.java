package data;

import RacingManager.SSCarro.Carro;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarroDAO {
    private static CarroDAO instance;
    private Connection connection;

    {
        try {
            connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> parseList(String listString) {
        List<Integer> list = new ArrayList<>();
        String[] items = listString.split(",");
        for (String item : items) {
            list.add(Integer.parseInt(item));
        }
        return list;
    }

    private List<Long> parseList2(String str) {
        List<Long> list = new ArrayList<>();
        String[] values = str.split(",");
        for (String value : values) {
            list.add(Long.parseLong(value));
        }
        return list;
    }

    private CarroDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement statement = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS cars " +
                    "(IdCarro TEXT PRIMARY KEY, " +
                    "voltas INTEGER NOT NULL, " +
                    "posicao INTEGER NOT NULL, " +
                    "tempo INTEGER NOT NULL, " +
                    "fiabilidade INTEGER NOT NULL, " +
                    "potencia INTEGER NOT NULL, " +
                    "cilindrada INTEGER NOT NULL, " +
                    "PAC FLOAT NOT NULL, " +
                    "pneus TEXT NOT NULL, " +
                    "motor TEXT NOT NULL, " +
                    "downforce FLOAT NOT NULL, " +
                    "categoria TEXT NOT NULL, " +
                    "DNF BOOLEAN NOT NULL, " +
                    "marca TEXT NOT NULL, " +
                    "modelo TEXT NOT NULL)";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static CarroDAO getInstance() {
        if (instance == null) {
            instance = new CarroDAO();
        }
        return instance;
    }

    public void put(Carro carro) {
        // Add a new car to the database
        try {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO cars (idCarro, voltas, posicao, tempo, fiabilidade, potencia, cilindrada, PAC, pneus, motor, downforce, categoria, DNF, marca, modelo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, carro.getIdCarro());
            statement.setInt(2, carro.getVoltas());
            statement.setArray(3, connection.createArrayOf("INTEGER", carro.getPosicao().toArray()));
            statement.setArray(4, connection.createArrayOf("BIGINT", carro.getTempo().toArray()));
            statement.setInt(5, carro.getFiabilidade());
            statement.setInt(6, carro.getPotencia());
            statement.setInt(7, carro.getCilindrada());
            statement.setFloat(8, carro.getPAC());
            statement.setString(9, carro.getPneus());
            statement.setString(10, carro.getMotor());
            statement.setFloat(11, carro.getDownforce());
            statement.setString(12, carro.getCategoria());
            statement.setBoolean(13, carro.getDNF());
            statement.setString(14, carro.getMarca());
            statement.setString(15, carro.getModelo());
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

    public Carro get(String idCarro) {
        // Retrieve a car from the database
        Carro carro = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM carros WHERE idCarro = ?");
            statement.setString(1, idCarro);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                carro = new Carro();
                carro.setVoltas(resultSet.getInt("voltas"));
                String posicaoString = resultSet.getString("posicao");
                if (posicaoString != null && !posicaoString.isEmpty()) {
                    carro.setPosicao(parseList(posicaoString));
                }
                String tempoString = resultSet.getString("tempo");
                if (tempoString != null && !tempoString.isEmpty()) {
                    carro.setTempo(parseList2(tempoString));
                }
                carro.setFiabilidade(resultSet.getInt("fiabilidade"));
                carro.setPotencia(resultSet.getInt("potencia"));
                carro.setCilindrada(resultSet.getInt("cilindrada"));
                carro.setPAC(resultSet.getFloat("PAC"));
                carro.setPneus(resultSet.getString("pneus"));
                carro.setMotor(resultSet.getString("motor"));
                carro.setDownforce(resultSet.getFloat("downforce"));
                carro.setCategoria(resultSet.getString("categoria"));
                carro.setDNF(resultSet.getBoolean("DNF"));
                carro.setMarca(resultSet.getString("marca"));
                carro.setModelo(resultSet.getString("modelo"));
                carro.setId(resultSet.getString("idCarro"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carro;
    }

    public void remove(String idCarro) {
        // Delete a car from the database
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM cars WHERE idCarro = ?");
            statement.setString(1, idCarro);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int size() {
        int size = 0;
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT COUNT(*) FROM carros")) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                size = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return size;
    }

    public void update(Carro carro) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE carros SET voltas = ?, posicao = ?, tempo = ?, fiabilidade = ?, potencia = ?, cilindrada = ?, PAC = ?, pneus = ?, motor = ?, downforce = ?, categoria = ?, DNF = ?, marca = ?, modelo = ? WHERE id = ?");
            statement.setInt(1, carro.getVoltas());
            statement.setArray(2, connection.createArrayOf("INTEGER", carro.getPosicao().toArray()));
            statement.setArray(3, connection.createArrayOf("BIGINT", carro.getTempo().toArray()));
            statement.setInt(4, carro.getFiabilidade());
            statement.setInt(5, carro.getPotencia());
            statement.setInt(6, carro.getCilindrada());
            statement.setFloat(7, carro.getPAC());
            statement.setString(8, carro.getPneus());
            statement.setString(9, carro.getMotor());
            statement.setFloat(10, carro.getDownforce());
            statement.setString(11, carro.getCategoria());
            statement.setBoolean(12, carro.getDNF());
            statement.setString(13, carro.getMarca());
            statement.setString(14, carro.getModelo());
            statement.setString(15, carro.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Carro> getAll() {
        Map<String, Carro> carros = new HashMap<>();
        try {
            connection.setAutoCommit(false);

            Statement statement = connection.createStatement();
            String sql = "SELECT idCarro, potencia, marca, modelo, categoria FROM cars";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String idCarro = resultSet.getString("idCarro");
                int potencia = resultSet.getInt("potencia");
                String marca = resultSet.getString("marca");
                String modelo = resultSet.getString("modelo");
                String categoria = resultSet.getString("categoria");

                Carro carro = new Carro();
                carro.setIdCarro(idCarro);
                carro.setPotencia(potencia);
                carro.setMarca(marca);
                carro.setModelo(modelo);
                carro.setCategoria(categoria);
                carros.put(idCarro, carro);
            }
            connection.commit();
        } catch (SQLException e) {
            // Erro a ler tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return carros;
    }
}