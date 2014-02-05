package Objets;

import BDD.Requete;
import Personnages.Personnage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Objet {
    private Image img;
    private int id;
    private String nom;
    private String desc;
    
    public Objet(int id) throws ClassNotFoundException, SQLException, SlickException {
        Requete rq = new Requete();
        ResultSet rs = rq.select("SELECT * FROM OBJET WHERE ID = " +id+ ";");
        
        this.id = id;
        img = new Image(rs.getString("IMG"));
        nom = rs.getString("NOM");
        desc = rs.getString("DESC");
        
        rq.closeDB();
    }
    
    public Image getImg() { return img; }
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getDesc() { return desc; }
    
    public void setId(int id) { this.id = id; }
    public void setImg(String chemin) {
        try {
            img = new Image(chemin);
        } catch (SlickException ex) {
            Logger.getLogger(Objet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void afficher(Graphics g, int x, int y) {
        g.drawImage(getImg(), x, y);
    }
    
    public boolean use(Personnage p) {
        //Return true si l'objet est utilisé, il sera alors supprimé de l'inventaire
        return false;
    }
    
    @Override
    public String toString() {
        return nom;
    }
}
