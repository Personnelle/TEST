package Carte;

import BDD.Requete;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Carte {
    //private final int id;
    private final Image img;
    private final List<Mur> murs = new ArrayList<>();
    
    public Carte(int id) throws ClassNotFoundException, SQLException, SlickException {
        this.id = id;
        
        Requete rq = new Requete();
        ResultSet rs = rq.select("SELECT IMG FROM CARTE WHERE ID = " +id+ ";");
        img = new Image(rs.getString("IMG"));
        
        rs = rq.select("SELECT ID FROM MUR WHERE IDMAP = " +id+ ";");
        while (rs.next())
            murs.add(new Mur(rs.getInt("ID")));
        
        rq.closeDB();
    }
    
    public int getId() { return id; }
    public Image getImage() { return img; }
    public List<Mur> getMurs() { return murs; }
    
    public void afficher(Graphics g) {
        g.drawImage(img, 0, 0);
        for (Mur m : murs) m.afficher(g);
    }
}