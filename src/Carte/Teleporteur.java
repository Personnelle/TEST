package Carte;

import BDD.Requete;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Teleporteur {
    private float x;
    private float y;
    private int id;
    private int idMap;
    private int idMapDest;
    private float xDest;
    private float yDest;
    private Image img;

    public Teleporteur(int id) throws ClassNotFoundException, SQLException, SlickException {
        Requete rq = new Requete();
        ResultSet rs = rq.select("SELECT * FROM TELEPORTEUR WHERE ID = " +id+ ";");
        
        this.id = id;
        idMap = rs.getInt("IDMAP");
        x = rs.getFloat("X");
        y = rs.getFloat("Y");
        idMapDest = rs.getInt("IDMAPDEST");
        xDest = rs.getFloat("XDEST");
        yDest = rs.getFloat("YDEST");
        img = new Image("ressources/autres/teleporteur.png");
        
        rq.closeDB();
    }
    
    public float getX() { return x; }
    public float getY() { return y; }
    public int getIdMap() { return idMap; }
    public int getIdMapDest() { return idMapDest; }
    public float getxDest() { return xDest; }
    public float getyDest() { return yDest; }
    public Image getImg() { return img; }
    public float getX1() { return x + img.getWidth(); }
    public float getY1() { return y + img.getHeight(); }
    
    public void affiche(Graphics g) {
        g.drawImage(img, x, y);
    }
}
