package data;

public class DAOconfig {
    static final String USERNAME = "bessa";                     // Actualizar
    static final String PASSWORD = "bessa";                     // Actualizar
    private static final String DATABASE = "RacingManager";     // Actualizar
    private static final String DRIVER = "jdbc:mariadb";        // Usar para MariaDB
    //private static final String DRIVER = "jdbc:mysql";        // Usar para MySQL
    static final String URL = DRIVER+"://localhost:3306/"+DATABASE;
}