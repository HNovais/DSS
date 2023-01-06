package data;

import RacingManager.Circuito;
import RacingManager.Elemento;

import java.sql.*;

import java.util.*;

public class ElementoDAO {
    private static ElementoDAO singleton = null;
    private Connection connection;

    {
        try {
            connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ElementoDAO() {
        // Initialize the DAO, create tables, etc.
    }

    public static ElementoDAO getInstance() {
        if (singleton == null) {
            singleton = new ElementoDAO();
        }
        return singleton;
    }

    public List<Elemento> getElementos(String circuitoID){
        List <Elemento> elementos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM elemento JOIN circuito ON elemento.circuitoNome = circuito.nomeCircuito WHERE circuito.nomeCircuito = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, circuitoID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Elemento elemento = new Elemento();
                elemento.setCategoria(resultSet.getString("categoria"));
                elemento.setGDU(resultSet.getString("GDU"));
                elementos.add(elemento);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }

        return elementos;
    }
}
