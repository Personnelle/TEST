package Carte;

import BDD.Requete;
import Constantes.Ctes;
import Personnages.Personnage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class TeleporteurList {
    private List<Teleporteur> liste = new ArrayList<>();
    
    public TeleporteurList(int idMap) throws ClassNotFoundException, SQLException, SlickException {
        Requete rq = new Requete();
        ResultSet rs = rq.select("SELECT ID FROM TELEPORTEUR WHERE IDMAP = " +idMap+ ";");
        
        while (rs.next())
            liste.add(new Teleporteur(rs.getInt("ID")));
        
        rq.closeDB();
    }
    
    public List<Teleporteur> getListe() { return liste; }
    
    public void affiche(Graphics g) {
        for (Teleporteur t : liste)
            t.affiche(g);
    }
    
    public int collision(Personnage p) {
        for (Teleporteur t : liste) {
            if (p.getX() < t.getX1() && p.getX1() > t.getX() && p.getY() < t.getY1() && p.getY1() > t.getY()) {
                p.setX(t.getxDest());
                p.setY(t.getyDest());
                return t.getIdMapDest();
            }
        }
        return Ctes.NOMAP_VALUE;
    }
}
