package data;

import RacingManager.Circuito;
import RacingManager.SSCampeonato.Campeonato;
import RacingManager.SSCorrida.Corrida;

import java.sql.*;

import java.util.*;

public class CampeonatoDAO {

    private static CampeonatoDAO singleton = null;
    private Connection connection;

    {
        try {
            connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private CampeonatoDAO() {
        // Initialize the DAO, create tables, etc.
    }

    public static CampeonatoDAO getInstance() {
        if (singleton == null) {
            singleton = new CampeonatoDAO();
        }
        return singleton;
    }

    public Campeonato get(String nome) {
        Campeonato campeonato = null;
        try {
            String sql = "SELECT * FROM campeonato WHERE nomeCampeonato = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nome);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nomeCampeonato = resultSet.getString("nomeCampeonato");
                //int participantes = resultSet.getInt("participantes");
                campeonato = new Campeonato();
                //campeonato.setParticipantes(participantes);
                campeonato.setNome(nomeCampeonato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return campeonato;
    }

    public void put(Campeonato campeonato) {
        try {
            String sql = "INSERT INTO campeonato (nomeCampeonato, participantes) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, campeonato.getNome());
            statement.setInt(2, campeonato.getParticipantes());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(String nome) {
        try {
            String sql = "DELETE FROM campeonato WHERE nomeCampeonato = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nome);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int size() {
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM campeonato";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void update(Campeonato campeonato) {
        try {
            String sql = "UPDATE campeonato SET participantes = ? WHERE nomeCampeonato = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, campeonato.getParticipantes());
            statement.setString(2, campeonato.getNome());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Para indicar os nomes dos campeonato nas opções de maneira a escolher um para configurar
    public List<String> getCampsName(){
        List<String> campNames = null;
        try {
            campNames = new ArrayList<>();
            String query = "SELECT nomeCampeonato FROM campeonato";
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                campNames.add(resultSet.getString("nomeCampeonato"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return campNames;
    }

    public Map<String, Campeonato> getAll() {
        Map<String, Campeonato> campeonatos = new HashMap<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT nomeCampeonato FROM campeonato");
            while (resultSet.next()) {
                String nome = resultSet.getString("nomeCampeonato");
                Campeonato campeonato = get(nome);
                campeonatos.put(nome, campeonato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return campeonatos;
    }

    private List<Corrida> parseList(String str) {
        // Parse a list of Corrida objects from a comma-separated string
        List<Corrida> corridas = new ArrayList<>();
        String[] values = str.split(",");
        for (String value : values) {
            corridas.add(CorridaDAO.getInstance().get(Integer.parseInt(value)));
        }
        return corridas;
    }

    public List<Circuito> getCircuitosCampeonato(Campeonato campeonato){
        List<Circuito> circuitos = new ArrayList<>();
        CircuitoDAO circuitoDAO = CircuitoDAO.getInstance();

        circuitos = circuitoDAO.getCircuitos(campeonato.getNome());

        return circuitos;
    }
}