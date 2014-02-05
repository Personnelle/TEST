package BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Requete {
    private final Connection c;
    private final Statement stmt;
    
    public Requete() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:bdd.db");
        stmt = c.createStatement();
    }
    
    public ResultSet select(String query) throws SQLException {
        return stmt.executeQuery(query);
    }
    
    public void request(String query) throws SQLException {
        stmt.executeUpdate(query);
    }
    
    public void closeDB() throws SQLException {
        stmt.close();
        c.close();
    }
}