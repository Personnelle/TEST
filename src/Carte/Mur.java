package Carte;

import BDD.Requete;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Mur {
    private final int id;
    private final int x;
    private final int y;
    private final Image img;
    
    public Mur(int id) throws ClassNotFoundException, SQLException, SlickException {
        this.id = id;
        
        Requete rq = new Requete();
        ResultSet rs = rq.select("SELECT * FROM MUR WHERE ID = " +id+ ";");
        
        x = rs.getInt("X");
        y = rs.getInt("Y");
        img = new Image(rs.getString("IMG"));
        
        rq.closeDB();
    }
    
    public int getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getX1() { return x + img.getWidth(); }
    public int getY1() { return y + img.getHeight(); }
    public int getLarg() { return img.getWidth(); }
    public int getHaut() { return img.getHeight(); }
    public Image getImg() { return img; }
    
    public void afficher(Graphics g) { g.drawImage(img, x, y); }
}