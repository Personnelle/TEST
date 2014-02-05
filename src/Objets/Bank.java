package Objets;

import BDD.Requete;
import Constantes.Ctes;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Bank {
    private List<Objet> objetsBank = new ArrayList<>();
    
    public Bank(int idPerso) throws ClassNotFoundException, SQLException, SlickException {
        Requete rq = new Requete();
        ResultSet rs = rq.select("SELECT * FROM BANK WHERE IDPERSO = " +idPerso+ ";");

        while (rs.next()) {
            if (rs.getInt("IDOBJET") < 0)
                objetsBank.add(new Objet(rs.getInt("IDOBJET")));
            else 
                objetsBank.add(new Equipement(rs.getInt("IDOBJET")));
        }

        rq.closeDB();
    }
    
    public void afficher(Graphics g) {
        int ligne = 1, colonne = 1;
        for (int i = 0 ; i < objetsBank.size() ; i++) {
            objetsBank.get(i).afficher(g, Ctes.BANK_X_SELECT + (colonne - 1) * Ctes.BANK_ESP_SELECT, Ctes.BANK_Y_SELECT + (ligne - 1) * Ctes.BANK_ESP_SELECT);
            colonne++;
            if (colonne == 6) {
                ligne++;
                colonne = 1;
            }
        }
    }
    
    public List<Objet> getListe() { return objetsBank; }
    public void add(Objet o) { objetsBank.add(o); }
    public void remove(Objet o) { objetsBank.remove(o); }
    public void remove(int i) { objetsBank.remove(i); }
}
